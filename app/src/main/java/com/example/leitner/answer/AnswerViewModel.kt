package com.example.leitner.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import kotlinx.coroutines.*

class AnswerViewModel(val cardIndex: Long,
                      val datasource: QuestionAnswerDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _gotoNexWord = MutableLiveData<Boolean>()
    val gotoNexWord: LiveData<Boolean>
        get() = _gotoNexWord

    private var _questionAnswer = MutableLiveData<QuestionAnswer>()
    val questionAnswer : LiveData<QuestionAnswer>
        get() = _questionAnswer

    private var _cardKey: Long

    init {
        //Log.i("AnswerViewModel", "theAnswerIndex: $theAnswerIndex")
        _cardKey = cardIndex
        getAnswer(_cardKey)
    }

    private fun getAnswer(key: Long) {
        uiScope.launch {
            val card = getAnswerDatabase(key)
            _questionAnswer.value = card
            //_selectedBox.value = card?.boxId
        }
    }

    private suspend fun getAnswerDatabase(key: Long): QuestionAnswer? {
        return withContext(Dispatchers.IO) {
            var card = datasource.getCardById(key)
            card
        }
    }

    private fun moveCardUp(key: Long) {
        uiScope.launch {
            moveCardUpDatabase(key)
        }
    }

    private suspend fun moveCardUpDatabase(key: Long) {
        withContext(Dispatchers.IO) {
            datasource.moveCardUp(key)
        }
    }

    private fun moveCardBack(key: Long) {
        uiScope.launch {
            moveCardBackDatabase(key)
        }
    }

    private suspend fun moveCardBackDatabase(key: Long) {
        withContext(Dispatchers.IO) {
            datasource.moveCardBack(key)
        }
    }

    fun onCorrect() {
        moveCardUp(_cardKey)
        _gotoNexWord.value = true
    }

    fun onWrong() {
        moveCardBack(_cardKey)
        _gotoNexWord.value = true
    }

    fun onNextWordComplete() {
        _gotoNexWord.value = false
    }
}