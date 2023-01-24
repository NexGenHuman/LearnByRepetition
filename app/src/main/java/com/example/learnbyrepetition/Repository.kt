package com.example.learnbyrepetition
/*
import android.app.Application
import androidx.annotation.RestrictTo.Scope
import androidx.lifecycle.LiveData
import com.example.learnbyrepetition.classes.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll

class Repository {

    private val flashcardDao: FlashcardDao
    private val flashcardSetDao: FlashcardSetDao
    private val intermediateFlashcardsSetsDao: IntermediateFlashcardsSetsDao

    private var allFlashcards: List<Flashcard>
    private var allFlashcardSets: List<FlashcardSet>
    private var allInternediateFlashcardsSets: List<IntermediateFlashcardsSets>

    constructor(app: Application) {
        var db = DatabaseFlashcards.getDatabase(app)

        flashcardDao = db.flashcardDao()
        flashcardSetDao = db.flashcardSetDao()
        intermediateFlashcardsSetsDao = db.intermediateFlashcardsSetsDao()

        allFlashcards = flashcardDao.getAll()
        allFlashcardSets = flashcardSetDao.getAll()
        allInternediateFlashcardsSets = intermediateFlashcardsSetsDao.getAll()
    }

    fun getAllFlashcards(): LiveData<List<Flashcard>> {
        return allFlashcards
    }

    fun getAllFlashcardSets(): LiveData<List<FlashcardSet>> {
        return allFlashcardSets
    }

    fun getAllIntermediateFlashcards(): LiveData<List<Flashcard>> {
        return allFlashcards
    }

    fun insertFlashcard(flashcard: Flashcard) {
        flashcardDao.insertAll(flashcard)
    }
}*/