package com.example.learnbyrepetition.newActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.databinding.ActivityAddFlashcardBinding
import com.example.learnbyrepetition.databinding.ActivityEditFlashcardBinding
import kotlinx.coroutines.launch

private lateinit var binding: ActivityEditFlashcardBinding

class EditFlashcardActivity : AppCompatActivity() {

    lateinit var flashcard: Flashcard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_flashcard)

        binding = ActivityEditFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch() {
            val db = DatabaseFlashcards.getDatabase(this@EditFlashcardActivity)
            val flashcardId =
                intent.extras?.getLong(getString(R.string.bundle_selected_flashcard_id))
            if (flashcardId == null) {
                Toast.makeText(this@EditFlashcardActivity, "Bundle ERROR", Toast.LENGTH_LONG)
                    .show()
                finish()
            } else {
                flashcard = db.flashcardDao().getById(flashcardId)
            }

            binding.editFlashcardEnglishText.setText(flashcard.englishText)
            binding.editFlashcardPolishText.setText(flashcard.polishMeaning)
            binding.editFlashcardIsWord.isChecked = flashcard.isWord
        }

        binding.buttonEditFlashcard.setOnClickListener(View.OnClickListener {
            flashcard.englishText = binding.editFlashcardEnglishText.text.toString()
            flashcard.polishMeaning = binding.editFlashcardPolishText.text.toString()
            flashcard.isWord = binding.editFlashcardIsWord.isChecked

            val context = this

            lifecycleScope.launch {
                if (flashcard.englishText.isEmpty() || flashcard.polishMeaning.isEmpty()) {
                    Toast.makeText(context, getString(R.string.wrong_data), Toast.LENGTH_SHORT)
                        .show()
                    return@launch
                }
                val state =
                    DatabaseFlashcards.getDatabase(context).flashcardDao().update(flashcard)
                if (state <= 0) {
                    Toast.makeText(context, getString(R.string.database_failed), Toast.LENGTH_LONG)
                        .show()
                    return@launch
                }
                finish()
            }
        })
    }
}