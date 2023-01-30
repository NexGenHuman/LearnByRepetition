package com.example.learnbyrepetition.newActivities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnbyrepetition.MyApplication
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.databinding.ActivityDetailsFlashcardBinding
import com.example.learnbyrepetition.ui.flashcardSets.FlashcardSetAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DetailsFlashcardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsFlashcardBinding
    lateinit var flashcard: Flashcard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_flashcard)

        binding = ActivityDetailsFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")


        lifecycleScope.launch() {
            val db = DatabaseFlashcards.getDatabase(this@DetailsFlashcardActivity)
            val flashcardId =
                intent.extras?.getLong(getString(R.string.bundle_selected_flashcard_id))
            if (flashcardId == null) {
                Toast.makeText(this@DetailsFlashcardActivity, "Bundle ERROR", Toast.LENGTH_LONG)
                    .show()
                finish()
            } else {
                flashcard = db.flashcardDao().getById(flashcardId)
            }

            binding.flashcardVisibleText.text = flashcard.englishText
            binding.flashcardTranslation.text = flashcard.polishMeaning
            binding.flashcardFailures.text = getString(R.string.failures) + flashcard.failureCount
            binding.flashcardSuccess.text = getString(R.string.successes) + flashcard.successCount
            if (!flashcard.isDataDefault) {
                binding.fabEditFlashcard.visibility = View.VISIBLE
                binding.fabDeleteFlashcard.visibility = View.VISIBLE
            }


            if (flashcard.dateLastStudied == null)
                binding.flashcardLastStudied.text = getString(R.string.never_studied)
            else
                binding.flashcardLastStudied.text = dateFormat.format(flashcard.dateLastStudied)
        }


        binding.flashcardCheck.setOnClickListener(View.OnClickListener {
            if (!binding.flashcardWordGuess.text.toString().equals(flashcard.polishMeaning, true)) {
                Toast.makeText(applicationContext, getString(R.string.wrong), Toast.LENGTH_SHORT)
                    .show()
                flashcard.failureCount += 1
                binding.flashcardFailures.text =
                    getString(R.string.failures) + flashcard.failureCount
            } else {
                Toast.makeText(applicationContext, getString(R.string.correct), Toast.LENGTH_SHORT)
                    .show()
                flashcard.successCount += 1
                binding.flashcardSuccess.text =
                    getString(R.string.successes) + flashcard.successCount
            }


            binding.flashcardCheck.visibility = View.GONE
            binding.flashcardWordGuess.visibility = View.GONE
            binding.flashcardTranslation.visibility = View.VISIBLE

            flashcard.dateLastStudied = Calendar.getInstance().time
            binding.flashcardLastStudied.text = dateFormat.format(flashcard.dateLastStudied)

            lifecycleScope.launch() {
                val db = DatabaseFlashcards.getDatabase(this@DetailsFlashcardActivity)
                db.flashcardDao().update(flashcard)
            }
        })

        binding.fabEditFlashcard.setOnClickListener(View.OnClickListener {
            val flashcardId =
                intent.extras?.getLong(getString(R.string.bundle_selected_flashcard_id))

            if (flashcardId == null) {
                Toast.makeText(this, getString(R.string.database_failed), Toast.LENGTH_LONG)
                    .show()
                return@OnClickListener
            }


            val intent = Intent(this, EditFlashcardActivity::class.java)

            var bundle = Bundle()
            bundle.putLong(this.getString(R.string.bundle_selected_flashcard_id), flashcardId)

            intent.putExtras(bundle)

            this.startActivity(intent)
            finish()
        })

        binding.fabDeleteFlashcard.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_flashcard))
                .setMessage(getString(R.string.delete_flashcard_info))
                .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    lifecycleScope.launch() {
                        val db = DatabaseFlashcards.getDatabase(this@DetailsFlashcardActivity)
                        db.flashcardDao().delete(flashcard)
                        Toast.makeText(this@DetailsFlashcardActivity, getString(R.string.data_deleted), Toast.LENGTH_LONG)
                        finish()
                    }
                })
                .setNegativeButton(getString(R.string.no), null)
                .show()
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if ((application as MyApplication).isTouchEnabled()) {
            super.dispatchTouchEvent(ev)
        } else {
            true
        }
    }
}