package com.example.learnbyrepetition.newActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.learnbyrepetition.MyApplication
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.database.classes.FlashcardSet
import com.example.learnbyrepetition.database.classes.IntermediateFlashcardsSets
import com.example.learnbyrepetition.databinding.ActivityAddFlashcardSetBinding
import com.example.learnbyrepetition.ui.flashcardSets.FlashcardSetsViewModel
import com.example.learnbyrepetition.ui.flashcards.FlashcardSelectionAdapter
import com.example.learnbyrepetition.ui.flashcards.SelectiveFlashcardsViewModel
import kotlinx.coroutines.launch

class AddFlashcardSetActivity : AppCompatActivity() {

    private lateinit var adapter: FlashcardSelectionAdapter
    private lateinit var binding: ActivityAddFlashcardSetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddFlashcardSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectiveFlashcardViewModel =
            ViewModelProvider(this)[SelectiveFlashcardsViewModel::class.java]

        lifecycleScope.launch() {
            selectiveFlashcardViewModel.RunDbQuery(this@AddFlashcardSetActivity)

            adapter = FlashcardSelectionAdapter(
                selectiveFlashcardViewModel.flashcards!!,
                this@AddFlashcardSetActivity
            )
            for ((index, value) in selectiveFlashcardViewModel.actives!!.withIndex())
                if (value) {
                    adapter.selectedFlashcards.add(selectiveFlashcardViewModel.flashcards!![index])
                }

            binding.newFlashcardSetFlashcardsSelection.adapter = adapter
            binding.newFlashcardSetFlashcardsSelection.layoutManager =
                LinearLayoutManager(
                    this@AddFlashcardSetActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            binding.newFlashcardAdd.setOnClickListener(View.OnClickListener {
                val name = binding.newFlashcardSetName.text.toString()
                val newFlashcardSet = FlashcardSet(name)

                lifecycleScope.launch() {
                    if (name.isEmpty()) {
                        Toast.makeText(
                            this@AddFlashcardSetActivity,
                            getString(R.string.wrong_data),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@launch
                    }

                    val db = DatabaseFlashcards.getDatabase(this@AddFlashcardSetActivity)
                    val state =
                        db.flashcardSetDao().insert(newFlashcardSet)
                    if (state < 0) {
                        Toast.makeText(
                            this@AddFlashcardSetActivity,
                            getString(R.string.database_failed),
                            Toast.LENGTH_LONG
                        )
                            .show()
                        return@launch
                    }

                    adapter.selectedFlashcards.forEach() {
                        val temp = IntermediateFlashcardsSets(state, it.id_flashcard)
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
