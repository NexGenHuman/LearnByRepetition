package com.example.learnbyrepetition.ui.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.classes.Flashcard

class FlashcardSimpleAdapter(var flashcards: List<Flashcard>) :
    RecyclerView.Adapter<FlashcardSimpleAdapter.FlashcardSimpleViewHolder>() {

    inner class FlashcardSimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val englishTextView: TextView
                val polishTextView: TextView

                var id: Long

                init {
                    englishTextView = itemView.findViewById(R.id.item_flashcard_simple_english)
                    polishTextView = itemView.findViewById(R.id.item_flashcard_simple_polish)

                    id = -1
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardSimpleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard_simple, parent, false)
        return FlashcardSimpleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

    override fun onBindViewHolder(holder: FlashcardSimpleViewHolder, position: Int) {

        with(flashcards[position]) {
            holder.englishTextView.text = englishText
            holder.polishTextView.text = polishMeaning

            holder.id = id_flashcard
        }
    }
}