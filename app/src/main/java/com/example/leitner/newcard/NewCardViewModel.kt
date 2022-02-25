package com.example.leitner.newcard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import kotlinx.coroutines.*

class NewCardViewModel(val datasource: QuestionAnswerDao,
                       val addEditType: String, val cardId: Long) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _goToQuestion = MutableLiveData<Boolean>()
    val goToQuestion: LiveData<Boolean>
        get() = _goToQuestion

    private var hasQuestion: Boolean = false
    private var hasAnswer: Boolean = false

    private val _isFormComplete = MutableLiveData<Boolean>()
    val isFormComplete: LiveData<Boolean>
        get() = _isFormComplete

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    val newQuestion = MutableLiveData<String>()
    val newAnswer = MutableLiveData<String>()
    val boxId = MutableLiveData<Int>()

    init {
        if (addEditType == "edit") {
            getValues()
        }
    }

    /**
     * called when button add or edit is clicked
     */
    fun onAddEditCard() {
        if (addEditType == "edit") {
            uiScope.launch {
                updateCardDatabase(cardId)
                _goToQuestion.value = true
            }
        } else {
            uiScope.launch {
                try {
                    addNewCardDatabase()
                    _goToQuestion.value = true
                } catch (e: Exception) {
                    Log.d("NewCardViewModel", "There was an exception: ${e.message}")
                    _errorMessage.value = e.message
                }
            }
        }
    }

    fun onQuestionTextChange(text: CharSequence) {
        hasQuestion = text.isNotEmpty()
        _isFormComplete.value = hasQuestion && hasAnswer
    }

    fun onAnswerTextChange(text: CharSequence) {
        hasAnswer = text.isNotEmpty()
        _isFormComplete.value = hasQuestion && hasAnswer
    }

    fun onGoToQuestionComplete() {
        _goToQuestion.value = false
    }

    fun onErroMessageDisplayed() {
        _errorMessage.value = ""
    }

    private fun getValues() {
        uiScope.launch {
            val questionAnswer = getCardFromDatabase()
            questionAnswer?.let {
                newQuestion.value = it.question
                newAnswer.value = it.answer
                boxId.value = it.boxId
            }
        }
    }

    private suspend fun getCardFromDatabase(): QuestionAnswer? {
        return withContext(Dispatchers.IO) {
            datasource.getCardById(cardId)
        }
    }

    /**
     * Insert a new card if there is not a similar card otherwise,
     * raise an exception if a similar card found.
     */
    private suspend fun addNewCardDatabase() {
        withContext(Dispatchers.IO) {
            val questionAnswer = QuestionAnswer(
                question = newQuestion.value.toString(),
                answer = newAnswer.value.toString()
            )
            //Log.d("NewCardViewModel", questionAnswer.createdMilli.toString())
            val theList = datasource.getSimilarQuestions(getQuestionPart(newQuestion.value.toString()))
            //Log.d("NewCardViewModel", "All Rows Count it: ${theList.count()}")
            if (theList.count() > 0 ) {
                throw Exception("Found similar words!")
            }
            datasource.insert(questionAnswer)
        }
    }

    private fun getQuestionPart(q: String): String {
        val qNew = q.substringAfter(':').trim()
        return "%$qNew"
    }

    private suspend fun updateCardDatabase(id: Long) {
        withContext(Dispatchers.IO) {
            val questionAnswer = datasource.getCardById(id)
            questionAnswer?.let {
                it.question = newQuestion.value.toString()
                it.answer = newAnswer.value.toString()
                datasource.update(it)
            }
        }
    }
}