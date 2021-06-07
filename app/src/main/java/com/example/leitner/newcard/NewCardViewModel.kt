package com.example.leitner.newcard

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import kotlinx.coroutines.*

class NewCardViewModel(
    val datasource: QuestionAnswerDao,
    val addEditType: String, val cardId: Long
) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val newQuestion = MutableLiveData<String>()
    val newAnswer = MutableLiveData<String>()

    init {
        if (addEditType == "edit") {
            getValues()
        }
    }

    /**
     * called when button_add is clicked
     */
    fun onAddNewCard() {
        if (addEditType == "edit") {
            uiScope.launch {
                updateCardDatabase(cardId)
            }
        } else {
            uiScope.launch {
                addNewCardDatabase()
            }
        }
    }

    private fun getValues() {
        uiScope.launch {
            val questionAnswer = getCardFromDatabase()
            questionAnswer?.let {
                newQuestion.value = it.question
                newAnswer.value = it.answer
            }
        }
    }
    private suspend fun getCardFromDatabase(): QuestionAnswer? {
        return withContext(Dispatchers.IO) {
            datasource.getCardById(cardId)
        }
    }

    private suspend fun addNewCardDatabase() {
        withContext(Dispatchers.IO) {
            val questionAnswer = QuestionAnswer(
                question = newQuestion.value.toString(),
                answer = newAnswer.value.toString()
            )
            datasource.insert(questionAnswer)
        }
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