package com.example.learnbyrepetition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Database
import com.example.learnbyrepetition.classes.Flashcard
import com.example.learnbyrepetition.classes.FlashcardDao
import com.example.learnbyrepetition.databinding.ActivityAddFlashcardBinding
import com.example.learnbyrepetition.databinding.ActivityMainBinding

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
                0,
                englishText,
                polishText,
                null,
                0,
                0,
                isWord,
                false)

            DatabaseFlashcards.getDatabase(this).flashcardDao().insertAll(newFlashcard)
            if(!DatabaseFlashcards.getDatabase(this).flashcardDao().getAll().value.isNullOrEmpty())
                Log.d("addFlashcard", "database isn't empty")
            else
                Log.d("addFlashcard", "EMPTY")

        }
    }
}