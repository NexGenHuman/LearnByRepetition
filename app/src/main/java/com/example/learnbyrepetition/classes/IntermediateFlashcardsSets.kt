package com.example.learnbyrepetition.classes

import androidx.room.*

const val INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME = "intermediateFlashcardsSets"

@Entity(
    tableName = INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME,
    primaryKeys = ["id_set", "id_flashcard"]
)
data class IntermediateFlashcardsSets(
    val id_set: Long,
    val id_flashcard: Long
)

data class SetWithFlashcards(
    @Embedded val flashcardSet: FlashcardSet,
    @Relation(
        parentColumn = "id_set",
        entityColumn = "id_flashcard",
        entity = Flashcard::class,
        associateBy = Junction(
            IntermediateFlashcardsSets::class,
            parentColumn = "id_set",
            entityColumn = "id_flashcard"
        )
    )
    val flashcards: List<Flashcard>
)

data class FlashcardInSets(
    @Embedded val flashcard: Flashcard,
    @Relation(
        parentColumn = "id_flashcard",
        entityColumn = "id_set",
        entity = FlashcardSet::class,
        associateBy = Junction(
            IntermediateFlashcardsSets::class,
            parentColumn = "id_flashcard",
            entityColumn = "id_set"
        )
    )
    val sets: List<FlashcardSet>
)

@Dao
interface IntermediateFlashcardsSetsDao : BaseDao<IntermediateFlashcardsSets> {

    @Query("SELECT * FROM $INTERMEDIATE_FLASHCARDS_SETS_TABLE_NAME")
    suspend fun getAll(): List<IntermediateFlashcardsSets>

    @Transaction
    @Query("SELECT * FROM $FLASHCARD_SETS_TABLE_NAME")
    suspend fun setWithFlashcards(): List<SetWithFlashcards>

    @Transaction
    @Query("SELECT * FROM $FLASHCARD_TABLE_NAME")
    suspend fun flashcardWithSets(): List<FlashcardInSets>
}