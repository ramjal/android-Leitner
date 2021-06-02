package com.example.leitner.newcard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leitner.database.QuestionAnswer

class NewCardViewModel : ViewModel() {

    private var _questionAnswer = MutableLiveData<QuestionAnswer>()
    val questionAnswer : LiveData<QuestionAnswer>
        get() = _questionAnswer

    /**
     * called when button_add is clicked
     */
    fun onAddNewCard() {
        Log.d("NewCardViewModel", "onAddNewCard")

    }

}