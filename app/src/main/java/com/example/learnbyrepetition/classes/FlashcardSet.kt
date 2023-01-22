package com.example.learnbyrepetition.classes

import androidx.lifecycle.LiveData
import androidx.room.*

const val FLASHCARD_SETS_TABLE_NAME = "flashcardSets"

@Entity(tableName = FLASHCARD_SETS_TABLE_NAME)
data class FlashcardSet (
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String
)

@Dao
interface FlashcardSetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg flashcardSets: FlashcardSet)

    @Update
    fun updateAll(vararg flashcardSets: FlashcardSet)

    @Delete
    fun delete(vararg flashcardSets: FlashcardSet)

    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME")
    fun getAll(): LiveData<List<FlashcardSet>>

    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME WHERE  id=:id")
    fun getById(id: Int): LiveData<FlashcardSet>
}