package com.example.leitner.answer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leitner.database.QuestionAnswerDao

class AnswerViewModelFactory(
        private val answerIndex: Int,
        private val dataSource: QuestionAnswerDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnswerViewModel::class.java)) {
            return AnswerViewModel(answerIndex, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}