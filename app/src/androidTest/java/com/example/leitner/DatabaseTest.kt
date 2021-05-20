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
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetOneRow() {
        //val qaObject = QuestionAnswer(qaId = 5, question = "testing", answer = "answer here")
        val qaObject = QuestionAnswer()
        questionAnswerDao.insert(qaObject)
        questionAnswerDao.insert(qaObject)
        questionAnswerDao.insert(qaObject)
        qaObject.qaId = 12
        questionAnswerDao.insert(qaObject)

        val questionAnswer = questionAnswerDao.getLastRow()
        Log.d("insertAndGetOneRow", "ID: ${questionAnswer.qaId}")
        assertEquals(questionAnswer?.answer, "")
    }

}