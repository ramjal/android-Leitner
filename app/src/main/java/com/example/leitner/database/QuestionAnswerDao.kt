package com.example.leitner.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface QuestionAnswerDao {
    @Insert
    fun insert(qAnda: QuestionAnswer)

    @Update
    fun update(qAnda: QuestionAnswer)

    @Delete
    fun delete(qAnda: QuestionAnswer)

    // Anything inside this method runs in a single transaction.
//    @Transaction
//    fun moveCard(oldQA: QuestionAnswer, newQA: QuestionAnswer) {
//        insert(newQA)
//        delete(oldQA)
//    }

    @Transaction
    fun moveCardUp(key: Long) {
        var theCard = getCardById(key)
        val oldID = theCard.uniqueId
        theCard.uniqueId = 0
        theCard.boxId++
        insert(theCard)
        deleteById(oldID)
    }

    @Transaction
    fun insertTempCards(questions: List<String>,  answers: List<String>) {
        for (i in questions.indices) {
            insert(QuestionAnswer(question = questions[i], answer = answers[i], boxId = 1))
        }
    }

    @Query("Select * from question_answer where uniqueId = :key")
    fun getCardById(key: Long) : QuestionAnswer

    @Query("Select * from question_answer where boxId = :boxId order by uniqueId limit 1")
    fun getFirstCardInBox(boxId: Int) : QuestionAnswer

    @Query("Select * from question_answer")
    fun getAllRows() : LiveData<List<QuestionAnswer>>

    @Query("select count(uniqueId) from question_answer")
    fun getTotalCount(): Int

    @Query("select count(uniqueId) from question_answer where boxId = :boxId")
    fun getCountByBox(boxId: Int): Int

    @Query("Delete from question_answer  where uniqueId = :key")
    fun deleteById(key: Long)

    @Query("Delete from question_answer")
    fun clear()
}