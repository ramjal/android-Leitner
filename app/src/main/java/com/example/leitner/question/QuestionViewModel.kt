package com.example.leitner.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel : ViewModel() {

    // The current question
    private var _question = MutableLiveData<String>()
    val question : LiveData<String>
        get() = _question

    init {
        _question.value = "contrived"
    }
}