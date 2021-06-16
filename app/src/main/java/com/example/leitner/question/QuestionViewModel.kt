package com.example.leitner.question

import android.app.Activity
import android.app.AlertDialog
import android.opengl.Visibility
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.*
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

    val bulletVisibility = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        //Log.i("QuestionViewModel", "_currIndex: ${_currIndex.value}")
        //insertTempCards()
        getTotalCount(boxId)
        getCurrentQuestion(boxId)
        bulletVisibility.value = true
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
     * called when one or the 5 top boxes is clicked
     */
    fun onBoxClicked(id: Int) {
        //Log.d("QuestionViewModel", "Id: ${id}")
        getTotalCount(id)
        getCurrentQuestion(id)
    }

    fun setVisibility(id: Int) : Boolean {
        //Log.d("QuestionViewModel", "Id: ${id}")
        return when (id) {
            1 -> true
            2 -> false
            3 -> true
            4 -> false
            else -> true
        }

    }

    /**
     * Check answer button events
     */
    fun onCheckAnswer() {
        _eventCheckAnswer.value = true
    }
    fun onCheckAnswerComplete() {
        _eventCheckAnswer.value = false
    }

    /**
     * add temp data to database
     */
    fun insertTempCards() {
        uiScope.launch {
            insertTempCardsToDatabase()
        }
    }
    private suspend fun insertTempCardsToDatabase() {
        return withContext(Dispatchers.IO) {
            datasource.insertTempCards(_questions, _answers)
        }
    }

    /**
     * delete all the cards from database
     */
    fun deleteCards() {
        uiScope.launch {
            deleteCardsFromDatabase()
        }
    }
    private suspend fun deleteCardsFromDatabase() {
        return withContext(Dispatchers.IO) {
            datasource.deleteAll()
        }
    }

}