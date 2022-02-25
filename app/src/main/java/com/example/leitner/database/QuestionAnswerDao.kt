package com.example.leitner.database

import androidx.lifecycle.LiveData
import androidx.room.*


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
        val theCard = getCardById(key)
        if (theCard != null) {
            val oldID = theCard.uniqueId
            theCard.uniqueId = 0
            theCard.boxId++
            theCard.createdMilli = System.currentTimeMillis()
            insert(theCard)
            deleteCardById(oldID)
        }
    }

    @Transaction
    fun moveCardToBox1(key: Long) {
        val theCard = getCardById(key)
        if (theCard != null) {
            val oldID = theCard.uniqueId
            theCard.uniqueId = 0
            theCard.boxId = 1
            theCard.createdMilli = System.currentTimeMillis()
            insert(theCard)
            deleteCardById(oldID)
        }
    }

    @Query("Select * from question_answer where question like :question")
    fun getSimilarQuestions(question: String) : List<QuestionAnswer>

    @Query("Select * from question_answer where uniqueId = :key")
    fun getCardById(key: Long) : QuestionAnswer?

    @Query("Select count(uniqueId) from question_answer where boxId = :boxId and createdMilli < :timeMilli")
    fun cardCountsRequiredViewing(boxId: Int, timeMilli: Long) : Int

    @Query("Select * from question_answer where boxId = :boxId and createdMilli < :timeMilli order by uniqueId limit 1")
    fun getFirstViewableCardInBox(boxId: Int, timeMilli: Long) : QuestionAnswer

    @Query("Select * from question_answer")
    fun getAllRows() : List<QuestionAnswer>

    @Query("select count(uniqueId) from question_answer")
    fun getTotalCount(): Int

    @Query("select count(uniqueId) from question_answer where boxId = :boxId")
    fun getCountByBox(boxId: Int): Int

    @Query("Delete from question_answer where uniqueId = :key")
    fun deleteCardById(key: Long)

    @Query("Delete from question_answer")
    fun deleteAll()
}