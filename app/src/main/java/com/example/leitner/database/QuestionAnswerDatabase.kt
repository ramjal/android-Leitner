package com.example.leitner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuestionAnswer::class], version = 1, exportSchema = false)
abstract class QuestionAnswerDatabase : RoomDatabase() {

    abstract val questionAnswerDao: QuestionAnswerDao

    companion object {
        @Volatile
        private var INSTANCE: QuestionAnswerDatabase? = null

        fun getInstance(context: Context) : QuestionAnswerDatabase {
            synchronized (this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionAnswerDatabase::class.java,
                        "question_answer_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}