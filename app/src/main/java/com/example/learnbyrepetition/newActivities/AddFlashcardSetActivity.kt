package com.example.learnbyrepetition.newActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.databinding.ActivityAddFlashcardSetBinding

class AddFlashcardSetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddFlashcardSetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddFlashcardSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TODO("Do eveerything about this activity")
    }
}