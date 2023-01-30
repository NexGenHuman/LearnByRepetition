package com.example.learnbyrepetition.ui.flashcards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.classes.Flashcard

class FlashcardSelectionAdapter(private val flashcards: List<Flashcard>, context: Context) :
    RecyclerView.Adapter<FlashcardSelectionAdapter.FlashcardSelectionViewHolder>() {

    val mContext = context
    val selectedFlashcards = mutableSetOf<Flashcard>()

    inner class FlashcardSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val englishTextView: TextView
        val polishTextView: TextView

        var id: Long

        init {
            englishTextView = itemView.findViewById(R.id.item_flashcard_simple_english)
            polishTextView = itemView.findViewById(R.id.item_flashcard_simple_polish)

            id = -1
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlashcardSelectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard_simple, parent, false)
        return FlashcardSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

    override fun onBindViewHolder(holder: FlashcardSelectionViewHolder, position: Int) {
        val flashcard = flashcards[position]

        with(flashcards[position]) {
            holder.englishTextView.text = englishText
            holder.polishTextView.text = polishMeaning

            holder.id = id_flashcard
        }

        if (flashcard in selectedFlashcards) {
            holder.itemView.isSelected = true
            holder.itemView.setBackgroundColor(mContext.resources.getColor(R.color.design_default_color_secondary))
        }

        holder.itemView.setOnClickListener {
            if (flashcard in selectedFlashcards) {
                selectedFlashcards.remove(flashcard)
                holder.itemView.isSelected = false
                holder.itemView.setBackgroundColor(mContext.resources.getColor(R.color.light_gray))
            } else {
                selectedFlashcards.add(flashcard)
                holder.itemView.isSelected = true
                holder.itemView.setBackgroundColor(mContext.resources.getColor(R.color.design_default_color_secondary))
            }
        }
    }
}