package com.example.learnbyrepetition.ui.flashcards

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.Flashcard

class FlashcardViewModel: ViewModel() {

    var flashcard: Flashcard? = null
    private var _id: Long = -1

    fun InitFlashcardId(id: Long) {
        _id = id
    }

    suspend fun RunDbQuery(context: Context) {
        if (_id != -1L && flashcard == null) {
            val db = DatabaseFlashcards.getDatabase(context)
            flashcard = db.flashcardDao().getById(_id)
        }
    }
}