package com.example.leitner.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leitner.answer.AnswerViewModel
import com.example.leitner.database.QuestionAnswerDao

class QuestionViewModelFactory(private val dataSource: QuestionAnswerDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}