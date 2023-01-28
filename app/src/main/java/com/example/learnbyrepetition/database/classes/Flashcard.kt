package com.example.learnbyrepetition.database.classes

import androidx.room.*
import java.util.Date

const val FLASHCARD_TABLE_NAME = "flashcards"

@Entity(tableName = FLASHCARD_TABLE_NAME)
data class Flashcard(
    var englishText: String,
    var polishMeaning: String,
    var dateLastStudied: Date?,
    var successCount: Int,
    var failureCount: Int,
    //Whether it's word or sentence
    var isWord: Boolean,
    //Whether it's a default data
    var isDataDefault: Boolean,

    @PrimaryKey(autoGenerate = true) var id_flashcard: Long = 0
)

@Dao
interface FlashcardDao : BaseDao<Flashcard> {

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME")
    suspend fun getAll(): List<Flashcard>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE id_flashcard=:id")
    suspend fun getById(id: Long): Flashcard

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isWord = 1 AND isDataDefault = 1")
    suspend fun getAllDefaultWords(): List<Flashcard>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isWord = 0 AND isDataDefault = 1")
    suspend fun getAllDefaultSentences(): List<Flashcard>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isWord = 1 AND isDataDefault = 0")
    suspend fun getAllOwnWords(): List<Flashcard>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isWord = 0 AND isDataDefault = 0")
    suspend fun getAllOwnSentences(): List<Flashcard>

    @Transaction
    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME INNER JOIN $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME ON $FLASHCARD_TABLE_NAME.id_flashcard = $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_flashcard WHERE $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_set = :flashcardSetId")
    suspend fun getFlashcardsByFlashcardSet(flashcardSetId: Long): List<Flashcard>

    @Transaction
    @Query("SELECT COUNT(*) FROM $FLASHCARD_TABLE_NAME INNER JOIN $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME ON $FLASHCARD_TABLE_NAME.id_flashcard = $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_flashcard WHERE $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME.id_set = :flashcardSetId")
    suspend fun getFlashcardsByFlashcardSetCount(flashcardSetId: Long): Int
}