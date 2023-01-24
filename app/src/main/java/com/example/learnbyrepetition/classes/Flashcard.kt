package com.example.learnbyrepetition.classes

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date

const val FLASHCARD_TABLE_NAME = "flashcards"

@Entity(tableName = FLASHCARD_TABLE_NAME)
data class Flashcard (
    val englishText: String,
    val polishMeaning: String,
    val dateLastStudied: Date?,
    val successCount: Int,
    val failureCount: Int,
    //Whether it's word or sentence
    val isWord: Boolean,
    //Whether it's a default data
    val isDataDefault: Boolean,

    @PrimaryKey(autoGenerate = true) val id_flashcard: Long = 0
    )

@Dao
interface FlashcardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg flashcards: Flashcard)

    @Update
    suspend fun updateAll(vararg flashcards: Flashcard)

    @Delete
    suspend fun delete(vararg flashcard: Flashcard)

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME")
    suspend fun getAll(): List<Flashcard>

}