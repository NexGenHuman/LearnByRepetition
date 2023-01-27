package com.example.learnbyrepetition.ui.flashcards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnbyrepetition.newActivities.AddFlashcardActivity
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.FlashcardAdapter
import com.example.learnbyrepetition.databinding.FragmentFlashcardsBinding
import kotlinx.coroutines.launch

class FlashcardsFragment : Fragment() {

    private lateinit var flashcardsViewModel: FlashcardsViewModel
    private var _binding: FragmentFlashcardsBinding? = null

    private val binding get() = _binding!!

    lateinit var mActivity: Activity

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        flashcardsViewModel = ViewModelProvider(this)[FlashcardsViewModel::class.java]

        _binding = FragmentFlashcardsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabAddFlashcard.setOnClickListener {
            var addFlashcardIntent = Intent(activity, AddFlashcardActivity::class.java)
            startActivity(addFlashcardIntent)
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch() {
            val db = DatabaseFlashcards.getDatabase(mActivity)

            val flashcardsDefaultWords = db.flashcardDao().getAllDefaultWords()
            val flashcardsDefaultSentences = db.flashcardDao().getAllDefaultSentences()
            val flashcardsOwnWords = db.flashcardDao().getAllOwnWords()
            val flashcardsOwnSentences = db.flashcardDao().getAllOwnSentences()

            binding.flashcardsDefaultWordsRecycler.adapter =
                FlashcardAdapter(flashcardsDefaultWords, mActivity)
            binding.flashcardsDefaultWordsRecycler.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            binding.flashcardsDefaultSentencesRecycler.adapter =
                FlashcardAdapter(flashcardsDefaultSentences, mActivity)
            binding.flashcardsDefaultSentencesRecycler.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            binding.flashcardsOwnWordsRecycler.adapter =
                FlashcardAdapter(flashcardsOwnWords, mActivity)
            binding.flashcardsOwnWordsRecycler.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            binding.flashcardsOwnSentencesRecycler.adapter =
                FlashcardAdapter(flashcardsOwnSentences, mActivity)
            binding.flashcardsOwnSentencesRecycler.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}