package com.example.learnbyrepetition.ui.flashcards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.learnbyrepetition.AddFlashcardActivity
import com.example.learnbyrepetition.MainActivity
import com.example.learnbyrepetition.databinding.FragmentFlashcardsBinding
import com.google.android.material.snackbar.Snackbar

class FlashcardsFragment : Fragment() {

    private  lateinit var flashcardsViewModel: FlashcardsViewModel
    private var _binding: FragmentFlashcardsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        flashcardsViewModel = ViewModelProvider(this)[FlashcardsViewModel::class.java]

        _binding = FragmentFlashcardsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabAddFlashcard.setOnClickListener { view ->
            var addFlashcardIntent = Intent(activity, AddFlashcardActivity::class.java)
            startActivity(addFlashcardIntent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}