package com.example.learnbyrepetition.classes

import androidx.lifecycle.LiveData
import androidx.room.*

const val INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME = "intermediateFlashcardsSets"

@Entity(tableName = INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME, primaryKeys = ["id_set","id_flashcard"])
data class IntermediateFlashcardsSets (
    var id_set: Long,
    var id_flashcard: Long
    )

data class SetWithFlashcards (
    @Embedded val flashcardSet: FlashcardSet,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_flashcard",
        associateBy = Junction(IntermediateFlashcardsSets::class)
    )
    val flashcards: List<Flashcard>
)

data class FlashcardInSets (
    @Embedded val flashcard: Flashcard,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_set",
        associateBy = Junction(IntermediateFlashcardsSets::class)
    )
    val sets: List<FlashcardSet>
)

@Dao
interface IntermediateFlashcardsSetsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg intermediateFlashcardsSets: IntermediateFlashcardsSets)

    @Update
    fun updateAll(vararg intermediateFlashcardsSets: IntermediateFlashcardsSets)

    @Delete
    fun delete(vararg intermediateFlashcardsSets: IntermediateFlashcardsSets)

    @Query("SELECT * FROM $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME")
    fun getAll(): LiveData<List<IntermediateFlashcardsSets>>

    @Transaction
    @Query("SELECT * FROM $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME WHERE id_set = :setId")
    fun getFlashcardsInSet(setId: Int): LiveData<List<SetWithFlashcards>>

    @Transaction
    @Query("SELECT * FROM $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME WHERE id_flashcard = :flashcardId")
    fun getSetsWithFlashcard(flashcardId: Int): LiveData<List<FlashcardInSets>>
}