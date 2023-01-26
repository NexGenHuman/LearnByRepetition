package com.example.learnbyrepetition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnbyrepetition.classes.Flashcard

class FlashcardAdapter(var flashcards: List<Flashcard>, mContext: Context) :
    RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    val context = mContext

    inner class FlashcardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView
        val ratioTextView: TextView
        val lastStudiedTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.item_flashcard_title)
            ratioTextView = itemView.findViewById(R.id.item_flashcard_fail_success_ratio)
            lastStudiedTextView = itemView.findViewById(R.id.item_flashcard_last_studied)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        with(flashcards[position]) {
            holder.titleTextView.text = englishText
            holder.ratioTextView.text = "Success $successCount/$failureCount Failures"
            holder.lastStudiedTextView.text =
                dateLastStudied?.toString() ?: context.getString(R.string.never_studied)
        }
    }
}