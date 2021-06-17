package com.example.leitner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "question_answer")
data class QuestionAnswer(

    @PrimaryKey(autoGenerate = true)
    var uniqueId: Long = 0L,

    var boxId: Int = 1,

    var question: String = "",

    var answer: String = "",

    var createdMilli: Long = System.currentTimeMillis()

)