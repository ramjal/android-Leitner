package com.example.leitner.newcard

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import kotlinx.coroutines.*

class NewCardViewModel(val datasource: QuestionAnswerDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val newQuestion = MutableLiveData<String>()
    val newAnswer = MutableLiveData<String>()

    private suspend fun addNewCardDatabase(questionAnswer: QuestionAnswer) {
        withContext(Dispatchers.IO) {
            datasource.insert(questionAnswer)
        }
    }

    /**
     * called when button_add is clicked
     */
    fun onAddNewCard() {
        uiScope.launch {
            val questionAnswer = QuestionAnswer(
                question = newQuestion.value.toString(),
                answer = newAnswer.value.toString())
            addNewCardDatabase(questionAnswer)
        }
    }

}