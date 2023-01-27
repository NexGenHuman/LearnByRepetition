package com.example.learnbyrepetition.database.classes

import androidx.room.*

const val FLASHCARD_SETS_TABLE_NAME = "flashcardSets"

@Entity(tableName = FLASHCARD_SETS_TABLE_NAME)
data class FlashcardSet(
    var name: String,
    
    @PrimaryKey(autoGenerate = true) var id_set: Long = 0
)

@Dao
interface FlashcardSetDao : BaseDao<FlashcardSet> {

    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME")
    suspend fun getAll(): List<FlashcardSet>
}