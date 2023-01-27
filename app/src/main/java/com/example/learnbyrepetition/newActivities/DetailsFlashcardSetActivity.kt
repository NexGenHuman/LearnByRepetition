package com.example.learnbyrepetition.newActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.FlashcardSet
import com.example.learnbyrepetition.databinding.ActivityDetailsFlashcardSetBinding
import kotlinx.coroutines.launch

class DetailsFlashcardSetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsFlashcardSetBinding
    lateinit var flashcardSet: FlashcardSet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_flashcard_set)

        binding = ActivityDetailsFlashcardSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch() {
            val db = DatabaseFlashcards.getDatabase(this@DetailsFlashcardSetActivity)
            val flashcardSetId =
                intent.extras?.getLong(getString(R.string.bundle_selected_flashcard_set_id))
            if (flashcardSetId == null) {
                Toast.makeText(this@DetailsFlashcardSetActivity, "Bundle ERROR", Toast.LENGTH_LONG)
                    .show()
                finish()
            } else {
                flashcardSet = db.flashcardSetDao().getById(flashcardSetId)
            }

            binding.
        }
    }
}