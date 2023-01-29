package com.example.learnbyrepetition.ui.flashcardSets

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.database.classes.FlashcardSet

class FlashcardSetsViewModel : ViewModel() {

    var flashcardSet: FlashcardSet? = null
    private var _id: Long = -1

    fun InitFlashcardSetId(id: Long) {
        _id = id
    }

    suspend fun RunDbQuery(context: Context) {
        if (_id != -1L && flashcardSet == null) {
            val db = DatabaseFlashcards.getDatabase(context)
            flashcardSet = db.flashcardSetDao().getById(_id)
        }
    }

}