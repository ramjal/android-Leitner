package com.example.leitner.question

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import com.example.leitner.database.Repos
import kotlinx.coroutines.*

class QuestionViewModel(val datasource: QuestionAnswerDao) : ViewModel()  {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // The current question
    private var _question = MutableLiveData<String>()
    val question : LiveData<String>
        get() = _question

    private var _totalCount = MutableLiveData<Int>()
    val totalCount : LiveData<Int>
        get() = _totalCount

    // The current question/answer index
    private var _currIndex = MutableLiveData<Int>()
    val currIndex : LiveData<Int>
        get() = _currIndex

    private val _eventCheckAnswer = MutableLiveData<Boolean>()
    val eventCheckAnswer: LiveData<Boolean>
        get() = _eventCheckAnswer

    // Temp repository
    private var _repos = Repos()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    init {
        //updateCurrent()
        //Log.i("QuestionViewModel", "_currIndex: ${_currIndex.value}")

        //insertTempCards()
        getTotalCount()
        getCurrentQuestion()
    }

    private fun getTotalCount() {
        uiScope.launch {
            _totalCount.value = getTotalCountFromDatabase()
        }
    }

    private suspend fun getTotalCountFromDatabase(): Int? {
        return withContext(Dispatchers.IO) {
            var count = datasource.getTotalCount()
            count
        }
    }

    private fun getCurrentQuestion() {
        uiScope.launch {
            _question.value = getCurrentQuestionFromDatabase()
        }
    }

    private suspend fun getCurrentQuestionFromDatabase(): String? {
        return withContext(Dispatchers.IO) {
            //var card = datasource.getFirstCardInBox(1)
            var card = datasource.getCardById(9)
            card?.question
        }
    }

    private fun insertTempCards() {
        uiScope.launch {
            insertTempCardsToDatabase()
        }
    }

    private suspend fun insertTempCardsToDatabase() {
        return withContext(Dispatchers.IO) {
            datasource.insertTempCards(_repos._questions, _repos._answers)
        }
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