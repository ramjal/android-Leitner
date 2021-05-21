package com.example.leitner

import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.leitner.database.QuestionAnswer
import com.example.leitner.database.QuestionAnswerDao
import com.example.leitner.database.QuestionAnswerDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var questionAnswerDao: QuestionAnswerDao
    private lateinit var db: QuestionAnswerDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, QuestionAnswerDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        questionAnswerDao = db.questionAnswerDao

        Log.i("createDb", "databse is created now!")
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        Log.i("closeDb", "databse is closing now!")
        db.close()
    }

    //If you want to order your tests (Which is not advised) use alphanumeric names order

    @Test
    @Throws(Exception::class)
    fun insert3RowsAndGetLastRow() {
        //val qaObject = QuestionAnswer(qaId = 5, question = "testing", answer = "answer here")
        questionAnswerDao.insert(QuestionAnswer(question = "q1"))
        questionAnswerDao.insert(QuestionAnswer(question = "q2", boxId = 2))
        questionAnswerDao.insert(QuestionAnswer(question = "q3"))

        var questionAnswer = questionAnswerDao.getFirstCardInBox(1)
        Log.d("insertAndGetOneRow", "Box1 cardId: ${questionAnswer.uniqueId}, Box1 question: ${questionAnswer.question}")
        assertEquals(questionAnswer.uniqueId, 1)

        questionAnswer = questionAnswerDao.getFirstCardInBox(2)
        Log.d("insertAndGetOneRow", "Box2 cardId: ${questionAnswer.uniqueId}, Box2 question: ${questionAnswer.question}")
        assertEquals(questionAnswer.uniqueId, 2)
    }

    @Test
    @Throws(Exception::class)
    fun getFirstRowAndUpdate() {
        var questionAnswer = QuestionAnswer(question = "First Row Question")
        questionAnswerDao.insert(questionAnswer)

        questionAnswer = questionAnswerDao.getCardById(1)
        Log.d("getFirstRowAndUpdate", "ID: ${questionAnswer.uniqueId}")
        Log.d("getFirstRowAndUpdate", "question: ${questionAnswer.question}")

        assertEquals(questionAnswer.question, "First Row Question")

        questionAnswer.question = "Updated!"
        questionAnswerDao.update(questionAnswer)

        assertEquals(questionAnswer.question, "Updated!")

        questionAnswer.boxId = 2
        questionAnswerDao.update(questionAnswer)

        questionAnswerDao.insert(QuestionAnswer(question = "test new", boxId = 2))
        questionAnswer = questionAnswerDao.getFirstCardInBox(2)

        assertEquals(questionAnswer.uniqueId, 1)
        assertEquals(questionAnswer.boxId, 2)
        assertEquals(questionAnswer.question, "Updated!")
    }

    @Test
    @Throws(Exception::class)
    fun moveCardUp() {
        var card1 = QuestionAnswer(question = "quest1", answer = "answ1")
        questionAnswerDao.insert(card1)

        card1 = questionAnswerDao.getCardById(1)
        var count = questionAnswerDao.getTotalCount()

        assertEquals(card1.uniqueId, 1)
        assertEquals(card1.boxId, 1)
        assertEquals(card1.question, "quest1")
        assertEquals(card1.answer, "answ1")
        assertEquals(count, 1)

        Log.d("moveCard","Card 1 => id:${card1.uniqueId}, box:${card1.boxId}, " +
                                    "ques:${card1.question}, ans:${card1.answer}")

        //var card2 = QuestionAnswer(boxId = card1.boxId + 1, question = card1.question, answer = card1.answer)
        var card2 = card1.copy(uniqueId = 0, card1.boxId + 1)//QuestionAnswer(boxId = card1.boxId + 1, question = card1.question, answer = card1.answer)
        questionAnswerDao.moveCard(card1, card2)

        val box1 = questionAnswerDao.getCountByBox(1)
        val box2 = questionAnswerDao.getCountByBox(2)

        card2 = questionAnswerDao.getCardById(2)
        count = questionAnswerDao.getTotalCount()

        assertEquals(card2.uniqueId, 2)
        assertEquals(card2.boxId, 2)
        assertEquals(card2.question, "quest1")
        assertEquals(card2.answer, "answ1")
        assertEquals(count, 1)

        Log.d("moveCard","Card 2 => id:${card2.uniqueId}, box:${card2.boxId}, " +
            "ques:${card2.question}, ans:${card2.answer}")

    }

}