package com.example.learnbyrepetition.classes

import androidx.lifecycle.LiveData
import androidx.room.*

const val FLASHCARD_SETS_TABLE_NAME = "flashcardSets"

@Entity(tableName = FLASHCARD_SETS_TABLE_NAME)
data class FlashcardSet (
    val name: String,

    @PrimaryKey(autoGenerate = true) val id_set: Long = 0
)

@Dao
interface FlashcardSetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg flashcardSets: FlashcardSet)

    @Update
    suspend fun updateAll(vararg flashcardSets: FlashcardSet)

    @Delete
    suspend fun delete(vararg flashcardSets: FlashcardSet)

    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME")
    suspend fun getAll(): List<FlashcardSet>
}