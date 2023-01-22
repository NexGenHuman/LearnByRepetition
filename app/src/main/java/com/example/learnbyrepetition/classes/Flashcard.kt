package com.example.learnbyrepetition.classes

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date

const val FLASHCARD_TABLE_NAME = "flashcards"

@Entity(tableName = FLASHCARD_TABLE_NAME)
data class Flashcard (
    @PrimaryKey(autoGenerate = true) var id: Int,
    var englishText: String,
    var polishMeaning: String,
    var dateLastStudied: Date?,
    var successCount: Int,
    var failureCount: Int,
    //Whether it's word or sentence
    var isWord: Boolean,
    //Whether it's a default data
    var isDataDefault: Boolean
    )

@Dao
interface FlashcardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg flashcards: Flashcard)

    @Update
    fun updateAll(vararg flashcards: Flashcard)

    @Delete
    fun delete(vararg flashcard: Flashcard)

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME")
    fun getAll(): LiveData<List<Flashcard>>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE  id=:id")
    fun getById(id: Int): LiveData<Flashcard>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isDataDefault = 1 AND isWord = 1")
    fun getAllDefaultWords(): LiveData<List<Flashcard>>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isDataDefault = 1 AND isWord = 0")
    fun getAllDefaultSentences(): LiveData<List<Flashcard>>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isDataDefault = 0 AND isWord = 1")
    fun getAllNonDefaultWords(): LiveData<List<Flashcard>>

    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME WHERE isDataDefault = 0 AND isWord = 0")
    fun getAllNonDefaultSentences(): LiveData<List<Flashcard>>

}