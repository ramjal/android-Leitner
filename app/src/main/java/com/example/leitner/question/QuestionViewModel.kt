package com.example.leitner.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.Repos

class QuestionViewModel : ViewModel() {
    //The current index
//    private var _index = MutableLiveData<Int>()
//    val index : LiveData<Int>
//        get() = _index

    // The current question
    private var _question = MutableLiveData<String>()
    val question : LiveData<String>
        get() = _question

    // The current answer
//    private var _answer = MutableLiveData<String>()
//    val answer : LiveData<String>
//        get() = _answer

    private val _arrayDeque = ArrayDeque<Int>()

    private var _currIndex = MutableLiveData<Int>()
    val currIndex : LiveData<Int>
        get() = _currIndex


//    private val _eventNexWord = MutableLiveData<Boolean>()
//    val eventNexWord: LiveData<Boolean>
//        get() = _eventNexWord
//
//    private val _questions =
//        listOf("contrived", "pat (adjective)", "mastered", "saucepan", "intermixture", "aught", "Jainism", "observation tower", "numbfish")
//
//    private val _answers =
//        listOf(
//        "deliberately created rather than arising naturally or spontaneously.",
//        "exactly suited to the purpose or occasion\n\nsuspiciously appropriate",
//        "To gain a thorough understanding of",
//        "A deep pan with a handle; used for stewing or boiling",
//        "An additional ingredient that is added by mixing with the base",
//        "No amount at all; something of no value or importance",
//        "Sect founded in the 6th century BC as a revolt against Hinduism",
//        "A structure commanding a wide view of its surroundings",
//        "Any sluggish bottom-dwelling ray of the order Torpediniformes having a rounded body and electric organs on each side of the head capable of emitting strong electric discharges"
//        )

    private var _repos = Repos()

    init {
        _arrayDeque.addAll(listOf(1,2,3,4,5,6,7,8,9))
        updateCurrent()

        //_index.value = _index.value ?: 1
        Log.i("QuestionViewModel", "_currIndex: ${_currIndex.value}")
    }

//    fun onNextWord() {
//        _index.value = _index.value?.plus(1)
//        _eventNexWord.value = true
//    }
//
//    fun onNextWordComplete() {
//        _eventNexWord.value = false
//    }

    fun onMoveToBackOfList() {
        _arrayDeque.addLast(_arrayDeque.removeFirst())
        updateCurrent()
    }

    private fun updateCurrent() {
        _currIndex.value = _arrayDeque.first()
        //_question.value = _questions.get(_arrayDeque.first()!!.minus(1))
        //_answer.value = _answers.get(_arrayDeque.first()!!.minus(1))
        _question.value = _repos.getQuestion(_arrayDeque.first()!!.minus(1))
        //_answer.value = _repos.getAnswer(_arrayDeque.first()!!.minus(1))

    }

}