package com.example.leitner.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionAnswerDao {
    @Insert
    fun insert(qAnda: QuestionAnswer)

    @Update
    fun update(qAnda: QuestionAnswer)

    @Query("Select * from question_answer where qa_id = :key")
    fun get(key: Long) : QuestionAnswer

    @Query("Select * from question_answer order by qa_id Desc limit 1")
    fun getLastRow() : QuestionAnswer


    @Query("Select * from question_answer")
    fun getAllRows() : LiveData<List<QuestionAnswer>>

    @Query("Delete from question_answer")
    fun clear()
}