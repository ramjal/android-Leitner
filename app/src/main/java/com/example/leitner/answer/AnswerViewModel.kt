package com.example.leitner.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import kotlinx.coroutines.*

class AnswerViewModel(cardIndex: Long,
                      val datasource: QuestionAnswerDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _gotoNexWord = MutableLiveData<Boolean>()
    val gotoNexWord: LiveData<Boolean>
        get() = _gotoNexWord

    private var _answer = MutableLiveData<String>()
    val answer : LiveData<String>
        get() = _answer

    //private var _repos = Repos()

    private var _cardKey: Long

    init {
        //Log.i("AnswerViewModel", "theAnswerIndex: $theAnswerIndex")
        //_answer.value = _repos.getAnswer(cardIndex.toInt().minus(1))
        _cardKey = cardIndex
        getAnswer(_cardKey)
    }

    private fun getAnswer(key: Long) {
        uiScope.launch {
            _answer.value = getAnswerDatabase(key)?.answer
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