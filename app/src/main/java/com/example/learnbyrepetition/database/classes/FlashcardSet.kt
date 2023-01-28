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

    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME WHERE id_set=:id")
    suspend fun getById(id: Long): FlashcardSet

    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME")
    suspend fun getAll(): List<FlashcardSet>

    @Transaction
    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME INNER JOIN $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME ON $FLASHCARD_SETS_TABLE_NAME.id_set = $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_set WHERE $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_flashcard = :flashcardId")
    fun getFlashcardSetsByFlashcard(flashcardId: Long): List<FlashcardSet>

    @Transaction
    @Query("SELECT COUNT(*) FROM $FLASHCARD_SETS_TABLE_NAME INNER JOIN $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME ON $FLASHCARD_SETS_TABLE_NAME.id_set = $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_set WHERE $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_flashcard = :flashcardId")
    fun getFlashcardSetsByFlashcardCount(flashcardId: Long): Int
}