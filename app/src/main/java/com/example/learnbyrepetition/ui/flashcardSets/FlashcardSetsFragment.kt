package com.example.learnbyrepetition.ui.flashcardSets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnbyrepetition.database.DatabaseFlashcards
import com.example.learnbyrepetition.databinding.FragmentFlashcardSetsBinding
import com.example.learnbyrepetition.newActivities.AddFlashcardSetActivity
import kotlinx.coroutines.launch

class FlashcardSetsFragment : Fragment() {

    private  var _binding: FragmentFlashcardSetsBinding? = null

    private val binding get() = _binding!!

    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mContext = layoutInflater.context

        _binding = FragmentFlashcardSetsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabAddFlashcardSet.setOnClickListener {
            var addFlashcardSetIntent = Intent(activity, AddFlashcardSetActivity::class.java)
            startActivity(addFlashcardSetIntent)
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch() {
            val db = DatabaseFlashcards.getDatabase(mContext)

            val flashcardSets = db.flashcardSetDao().getAll()

            binding.flashcardSetsRecycler.adapter =
                FlashcardSetAdapter(flashcardSets, mContext)
            binding.flashcardSetsRecycler.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}