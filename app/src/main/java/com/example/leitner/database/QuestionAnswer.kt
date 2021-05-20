package com.example.leitner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//indices = [Index(value = ["question", "answer"], unique = true)])
@Entity(tableName = "question_answer")
data class QuestionAnswer(

    @ColumnInfo(name = "qa_id")
    @PrimaryKey(autoGenerate = true)
    var qaId: Long = 0L,

    @ColumnInfo(name = "question")
    var question: String = "",

    @ColumnInfo(name = "answer")
    var answer: String = ""

)