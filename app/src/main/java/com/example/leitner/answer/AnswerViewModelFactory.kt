package com.example.leitner.answer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnswerViewModelFactory(private val theAnswer: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnswerViewModel::class.java)) {
            return AnswerViewModel(theAnswer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}