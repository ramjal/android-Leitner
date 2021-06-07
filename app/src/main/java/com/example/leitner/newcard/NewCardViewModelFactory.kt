package com.example.leitner.newcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leitner.answer.AnswerViewModel
import com.example.leitner.database.QuestionAnswerDao

class NewCardViewModelFactory(val dataSource: QuestionAnswerDao,
                              val addEditType: String, val cardId: Long)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewCardViewModel(dataSource, addEditType, cardId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}