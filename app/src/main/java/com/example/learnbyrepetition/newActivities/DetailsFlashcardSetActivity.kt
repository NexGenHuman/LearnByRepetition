package com.example.learnbyrepetition.newActivities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.FlashcardSet
import com.example.learnbyrepetition.databinding.ActivityDetailsFlashcardSetBinding
import com.example.learnbyrepetition.ui.flashcardSets.FlashcardSetAdapter
import com.example.learnbyrepetition.ui.flashcards.FlashcardSimpleAdapter
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

            binding.flashcardSetTitle.text =  flashcardSet.name
        }

        binding.fabEditFlashcardSet.setOnClickListener(View.OnClickListener {
            val flashcardSetId =
                intent.extras?.getLong(getString(R.string.bundle_selected_flashcard_set_id))

            if (flashcardSetId == null) {
                Toast.makeText(this, getString(R.string.database_failed), Toast.LENGTH_LONG)
                    .show()
                return@OnClickListener
            }

            val intent = Intent(this, EditFlashcardSetActivity::class.java)

            var bundle = Bundle()

            intent.putExtras(bundle)

            this.startActivity(intent)
            finish()
        })

        binding.fabDeleteFlashcardSet.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_flashcard_set))
                .setMessage(getString(R.string.delete_flashcard_set_info))
                .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener() {dialogInterface: DialogInterface, i: Int ->
                    lifecycleScope.launch() {
                        val db = DatabaseFlashcards.getDatabase(this@DetailsFlashcardSetActivity)
                        db.flashcardSetDao().delete(flashcardSet)
                        Toast.makeText(this@DetailsFlashcardSetActivity, getString(R.string.data_deleted), Toast.LENGTH_LONG)
                        finish()
                    }
                })
                .setNegativeButton(getString(R.string.no), null)
                .show()
        })
    }



    override fun onResume() {
        super.onResume()

        lifecycleScope.launch() {
            val db = DatabaseFlashcards.getDatabase(this@DetailsFlashcardSetActivity)

            val flashcardSetId =
                intent.extras?.getLong(getString(R.string.bundle_selected_flashcard_set_id))

            val flashcards = db.flashcardDao().getFlashcardsByFlashcardSet(flashcardSetId!!)

            binding.flashcardSetFlashcardRecycler.adapter =
                FlashcardSimpleAdapter(flashcards)
            binding.flashcardSetFlashcardRecycler.layoutManager =
                LinearLayoutManager(this@DetailsFlashcardSetActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}