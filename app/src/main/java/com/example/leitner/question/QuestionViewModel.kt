package com.example.leitner.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.Repos

class QuestionViewModel : ViewModel() {
    // The current question
    private var _question = MutableLiveData<String>()
    val question : LiveData<String>
        get() = _question

    // The current question/answer index
    private var _currIndex = MutableLiveData<Int>()
    val currIndex : LiveData<Int>
        get() = _currIndex

    private val _eventCheckAnswer = MutableLiveData<Boolean>()
    val eventCheckAnswer: LiveData<Boolean>
        get() = _eventCheckAnswer

    // Temp repository
    private var _repos = Repos()

    init {
        updateCurrent()
        //Log.i("QuestionViewModel", "_currIndex: ${_currIndex.value}")
    }

    fun onMoveToBackOfList() {
        _repos.moveToBackOfList()
        updateCurrent()
    }

    private fun updateCurrent() {
        _currIndex.value = _repos.getCurrIndex()
        _question.value = _repos.getQuestion(_currIndex.value!!.minus(1))
    }

    fun onCheckAnswer() {
        _eventCheckAnswer.value = true
    }

    fun onCheckAnswerComplete() {
        _eventCheckAnswer.value = false
    }
}