package com.example.leitner.answer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnswerViewModelFactory(private val answerIndex: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnswerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnswerViewModel(answerIndex) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}