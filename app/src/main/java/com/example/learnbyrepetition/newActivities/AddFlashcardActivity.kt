package com.example.learnbyrepetition.newActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.learnbyrepetition.MyApplication
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.databinding.ActivityAddFlashcardBinding
import kotlinx.coroutines.launch

class AddFlashcardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFlashcardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNewFlashcard.setOnClickListener {
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
                false
            )

            val context = this

            lifecycleScope.launch {
                if (englishText.isEmpty() || polishText.isEmpty()) {
                    Toast.makeText(context, getString(R.string.wrong_data), Toast.LENGTH_SHORT)
                        .show()
                    return@launch
                }
                val state =
                    DatabaseFlashcards.getDatabase(context).flashcardDao().insert(newFlashcard)
                if (state < 0) {
                    Toast.makeText(context, getString(R.string.database_failed), Toast.LENGTH_LONG)
                        .show()
                    return@launch
                }
                finish()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if ((application as MyApplication).isTouchEnabled()) {
            super.dispatchTouchEvent(ev)
        } else {
            true
        }
    }
}