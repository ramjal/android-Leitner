package com.example.leitner.answer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.Repos

class AnswerViewModel(theAnswerIndex: Int) : ViewModel() {

//    private var _index = MutableLiveData<Int>()
//    val index : LiveData<Int>
//        get() = _index

    private val _eventNexWord = MutableLiveData<Boolean>()
    val eventNexWord: LiveData<Boolean>
        get() = _eventNexWord



    private var _answer = MutableLiveData<String>()
    val answer : LiveData<String>
        get() = _answer

    private var _repos = Repos()

    init {
        Log.i("AnswerViewModel", "theAnswerIndex: $theAnswerIndex")
        _answer.value = _repos.getAnswer(theAnswerIndex)
    }


    fun onNextWord() {
        //_index.value = _index.value?.plus(1)
        _eventNexWord.value = true
    }

    fun onNextWordComplete() {
        _eventNexWord.value = false
    }
}