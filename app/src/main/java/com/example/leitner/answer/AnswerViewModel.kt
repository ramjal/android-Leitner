package com.example.leitner.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnswerViewModel(theAnswer: String) : ViewModel() {

    private var _answer = MutableLiveData<String>()
    val answer : LiveData<String>
        get() = _answer


    init {
        _answer.value = theAnswer
    }

}