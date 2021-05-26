package com.example.leitner.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswerDao
import com.example.leitner.database.Repos
import kotlinx.coroutines.*

class AnswerViewModel(cardIndex: Long,
                      val datasource: QuestionAnswerDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _eventNexWord = MutableLiveData<Boolean>()
    val eventNexWord: LiveData<Boolean>
        get() = _eventNexWord

    private var _answer = MutableLiveData<String>()
    val answer : LiveData<String>
        get() = _answer

    private var _repos = Repos()

    private var _cardKey: Long

    init {
        //Log.i("AnswerViewModel", "theAnswerIndex: $theAnswerIndex")
        _answer.value = _repos.getAnswer(cardIndex.toInt())
        _cardKey = cardIndex + 1
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

    fun onPassed() {
        moveCardUp(_cardKey)
        _eventNexWord.value = true
    }

    fun onRecycle() {
        //moveCardUp(_cardKey)
        _eventNexWord.value = true
    }

    fun onNextWordComplete() {
        _eventNexWord.value = false
    }
}