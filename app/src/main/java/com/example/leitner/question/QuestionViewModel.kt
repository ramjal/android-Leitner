package com.example.leitner.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel : ViewModel() {

    // The current question
    private var _question = MutableLiveData<String>()
    val question : LiveData<String>
        get() = _question

    // The current answer
    private var _answer = MutableLiveData<String>()
    val answer : LiveData<String>
        get() = _answer

    private val _map = mutableMapOf<String, String>()

    init {
        _map["contrived"] = "deliberately created rather than arising naturally or spontaneously.\n\n" +
            "created or arranged in a way that seems artificial and unrealistic.\n\n" +
            "\"the ending of the novel is too pat and contrived\""

        _question.value = "contrived"
        _answer.value = _map["contrived"]

    }

}