package com.example.learnbyrepetition.ui.flashcards

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.database.classes.FlashcardSet

class SelectiveFlashcardsViewModel : ViewModel() {
    var flashcards: List<Flashcard>? = null
    var actives: List<Boolean>? = null

    suspend fun RunDbQuery(context: Context, activesToSeed: List<Flashcard>? = null) {
        if (flashcards == null) {
            val db = DatabaseFlashcards.getDatabase(context)
            flashcards = db.flashcardDao().getAll()
            actives = List(flashcards!!.size) { false }

            Log.d("EditFlashcardSet", "All flashcards: " + flashcards!!.size)

            if (activesToSeed != null) {
                actives = List(flashcards!!.size) { flashcards!![it] in activesToSeed }
                Log.d("EditFlashcardSet", "Here")
            }
        }
    }

}