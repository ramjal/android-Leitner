package com.example.leitner.question

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import kotlinx.coroutines.*

class QuestionViewModel(val boxId: Int,
                        val datasource: QuestionAnswerDao) : ViewModel()  {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _questionAnswer = MutableLiveData<QuestionAnswer>()
    val questionAnswer : LiveData<QuestionAnswer>
        get() = _questionAnswer

    private var _totalCount = MutableLiveData<Int>()
    val totalCount : LiveData<Int>
        get() = _totalCount

    private val _eventCheckAnswer = MutableLiveData<Boolean>()
    val eventCheckAnswer: LiveData<Boolean>
        get() = _eventCheckAnswer

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        //Log.i("QuestionViewModel", "_currIndex: ${_currIndex.value}")
        //insertTempCards()
        getTotalCount(boxId)
        getCurrentQuestion(boxId)
    }

    private fun getTotalCount(boxId: Int) {
        uiScope.launch {
            _totalCount.value = getTotalCountFromDatabase(boxId)
        }
    }
    private suspend fun getTotalCountFromDatabase(boxId: Int): Int? {
        return withContext(Dispatchers.IO) {
            var count = datasource.getCountByBox(boxId)
            count
        }
    }

    private fun getCurrentQuestion(boxId: Int) {
        uiScope.launch {
            _questionAnswer.value = getFirstQuestionFromDatabase(boxId)
        }
    }
    private suspend fun getFirstQuestionFromDatabase(boxId: Int): QuestionAnswer? {
        return withContext(Dispatchers.IO) {
            var card = datasource.getFirstCardInBox(boxId)
            card
        }
    }

    /**
     * called when a top box is clicked
     */
    fun onBoxClicked(id: Int) {
        Log.d("QuestionViewModel", "Id: ${id}")
        getTotalCount(id)
        getCurrentQuestion(id)
    }

    fun onCheckAnswer() {
        _eventCheckAnswer.value = true
    }

    fun onCheckAnswerComplete() {
        _eventCheckAnswer.value = false
    }

}