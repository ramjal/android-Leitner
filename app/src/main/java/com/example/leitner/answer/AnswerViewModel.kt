package com.example.leitner.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.Repos

class AnswerViewModel(theAnswerIndex: Int) : ViewModel() {

    private val _eventNexWord = MutableLiveData<Boolean>()
    val eventNexWord: LiveData<Boolean>
        get() = _eventNexWord

    private var _answer = MutableLiveData<String>()
    val answer : LiveData<String>
        get() = _answer

    private var _repos = Repos()

    init {
        //Log.i("AnswerViewModel", "theAnswerIndex: $theAnswerIndex")
        _answer.value = _repos.getAnswer(theAnswerIndex)
    }

    fun onNextWord() {
        _eventNexWord.value = true
    }

    fun onNextWordComplete() {
        _eventNexWord.value = false
    }
}