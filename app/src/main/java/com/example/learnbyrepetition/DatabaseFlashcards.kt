package com.example.learnbyrepetition

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.learnbyrepetition.classes.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Flashcard::class, FlashcardSet::class, IntermediateFlashcardsSets::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DatabaseFlashcards: RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
    abstract fun flashcardSetDao(): FlashcardSetDao
    abstract fun intermediateFlashcardsSetsDao(): IntermediateFlashcardsSetsDao

    companion object{
        @Volatile
        private var INSTANCE: DatabaseFlashcards? = null

        fun getDatabase(context: Context): DatabaseFlashcards {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseFlashcards::class.java,
                    "database_flashcards"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}