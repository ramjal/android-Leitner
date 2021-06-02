package com.example.leitner.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.Instant
import java.time.LocalDate
import java.util.*


@Dao
interface QuestionAnswerDao {

    @Transaction
    fun insertTempCards(questions: List<String>,  answers: List<String>) {
        if (getTotalCount() == 0) {
            for (i in questions.indices) {
                insert(QuestionAnswer(question = questions[i], answer = answers[i], boxId = 1))
            }
        }
    }

    @Insert
    fun insert(qAnda: QuestionAnswer)

    @Update
    fun update(qAnda: QuestionAnswer)

    @Delete
    fun delete(qAnda: QuestionAnswer)

    @Transaction
    fun moveCardUp(key: Long) {
        var theCard = getCardById(key)
        if (theCard != null) {
            val oldID = theCard.uniqueId
            theCard.uniqueId = 0
            theCard.boxId++
            insert(theCard)
            deleteCardById(oldID)
        }
    }

    @Transaction
    fun moveCardBox1(key: Long) {
        var theCard = getCardById(key)
        if (theCard != null) {
            val oldID = theCard.uniqueId
            theCard.uniqueId = 0
            theCard.boxId = 1
            insert(theCard)
            deleteCardById(oldID)
        }
    }

    @Query("Select * from question_answer where uniqueId = :key")
    fun getCardById(key: Long) : QuestionAnswer?

    @Query("Select * from question_answer where boxId = :boxId order by uniqueId limit 1")
    fun getFirstCardInBox(boxId: Int) : QuestionAnswer

    @Query("Select * from question_answer")
    fun getAllRows() : LiveData<List<QuestionAnswer>>

    @Query("select count(uniqueId) from question_answer")
    fun getTotalCount(): Int

    @Query("select count(uniqueId) from question_answer where boxId = :boxId")
    fun getCountByBox(boxId: Int): Int

    @Query("Delete from question_answer  where uniqueId = :key")
    fun deleteCardById(key: Long)

    @Query("Delete from question_answer")
    fun deleteAll()
}