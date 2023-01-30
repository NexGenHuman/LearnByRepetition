package com.example.learnbyrepetition.newActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnbyrepetition.MyApplication
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.database.classes.FlashcardSet
import com.example.learnbyrepetition.database.classes.IntermediateFlashcardsSets
import com.example.learnbyrepetition.databinding.ActivityAddFlashcardSetBinding
import com.example.learnbyrepetition.databinding.ActivityEditFlashcardSetBinding
import com.example.learnbyrepetition.ui.flashcardSets.FlashcardSetsViewModel
import com.example.learnbyrepetition.ui.flashcards.FlashcardSelectionAdapter
import com.example.learnbyrepetition.ui.flashcards.SelectiveFlashcardsViewModel
import kotlinx.coroutines.launch

class EditFlashcardSetActivity : AppCompatActivity() {

    private lateinit var adapter: FlashcardSelectionAdapter
    private lateinit var binding: ActivityEditFlashcardSetBinding
    lateinit var flashcardSet: FlashcardSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_flashcard_set)

        binding = ActivityEditFlashcardSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectiveFlashcardViewModel =
            ViewModelProvider(this)[SelectiveFlashcardsViewModel::class.java]
        var flashcardSetsViewModel =
            ViewModelProvider(this)[FlashcardSetsViewModel::class.java]

        lifecycleScope.launch() {
            val flashcardSetId =
                intent.extras?.getLong(getString(R.string.bundle_selected_flashcard_set_id))

            flashcardSetsViewModel.InitFlashcardSetId(flashcardSetId!!)
            flashcardSetsViewModel.RunDbQuery(this@EditFlashcardSetActivity)

            val db = DatabaseFlashcards.getDatabase(this@EditFlashcardSetActivity)

            flashcardSet = db.flashcardSetDao().getById(flashcardSetId!!)
            val flashcardsInSet =
                db.flashcardDao()
                    .getFlashcardsByFlashcardSet(flashcardSetId!!)
            selectiveFlashcardViewModel.RunDbQuery(this@EditFlashcardSetActivity, flashcardsInSet)

            adapter = FlashcardSelectionAdapter(
                selectiveFlashcardViewModel.flashcards!!,
                this@EditFlashcardSetActivity
            )
            for ((index, value) in selectiveFlashcardViewModel.actives!!.withIndex())
                if (value) {
                    adapter.selectedFlashcards.add(selectiveFlashcardViewModel.flashcards!![index])
                }

            binding.editFlashcardSetName.setText(flashcardSetsViewModel.flashcardSet!!.name)

            Log.d("EditFlashcardSet", "setId: ${flashcardSetsViewModel.flashcardSet!!.id_set}")
            Log.d("EditFlashcardSet", "name: ${flashcardSetsViewModel.flashcardSet!!.name}")
            Log.d("EditFlashcardSet", "num of flashcards before: ${selectiveFlashcardViewModel.flashcards!!.size}")
            Log.d("EditFlashcardSet", "num of flashcards: ${adapter.selectedFlashcards.size}")

            binding.editFlashcardSetFlashcardsSelection.adapter = adapter
            binding.editFlashcardSetFlashcardsSelection.layoutManager =
                LinearLayoutManager(
                    this@EditFlashcardSetActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            binding.editFlashcardSave.setOnClickListener(View.OnClickListener {
                flashcardSet.name = binding.editFlashcardSetName.text.toString()

                lifecycleScope.launch() {
                    if (flashcardSet.name.isEmpty()) {
                        Toast.makeText(
                            this@EditFlashcardSetActivity,
                            getString(R.string.wrong_data),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@launch
                    }

                    val db = DatabaseFlashcards.getDatabase(this@EditFlashcardSetActivity)
                    db.intermediateFlashcardsSetsDao().deleteFlashcardSetsBySetId(flashcardSet.id_set)

                    adapter.selectedFlashcards.forEach() {
                        val temp = IntermediateFlashcardsSets(flashcardSet.id_set, it.id_flashcard)
                        db.intermediateFlashcardsSetsDao().insert(temp)
                    }

                    finish()
                }
            })
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var selectiveFlashcardViewModel =
            ViewModelProvider(this)[SelectiveFlashcardsViewModel::class.java]
        var flashcardSetsViewModel =
            ViewModelProvider(this)[FlashcardSetsViewModel::class.java]


        flashcardSetsViewModel.flashcardSet!!.name = binding.editFlashcardSetName.text.toString()

        val tempActives =
            List(selectiveFlashcardViewModel.flashcards!!.size) { selectiveFlashcardViewModel.flashcards!![it] in adapter.selectedFlashcards }
        selectiveFlashcardViewModel.actives = tempActives
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if ((application as MyApplication).isTouchEnabled()) {
            super.dispatchTouchEvent(ev)
        } else {
            true
        }
    }
}