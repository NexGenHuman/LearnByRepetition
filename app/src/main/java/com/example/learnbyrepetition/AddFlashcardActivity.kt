package com.example.learnbyrepetition

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.learnbyrepetition.classes.Flashcard
import com.example.learnbyrepetition.databinding.ActivityAddFlashcardBinding
import kotlinx.coroutines.launch

class AddFlashcardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFlashcardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNewFlashcard.setOnClickListener { view ->
            val englishText = binding.newFlashcardEnglishText.text.toString()
            val polishText = binding.newFlashcardPolishText.text.toString()
            val isWord = binding.newFlashcardIsWord.isChecked
            var newFlashcard = Flashcard(
                englishText,
                polishText,
                null,
                0,
                0,
                isWord,
                false)

            val context = this

            lifecycleScope.launch {
                DatabaseFlashcards.getDatabase(context).flashcardDao().insertAll(newFlashcard)
            }


        }
    }
}