package com.example.learnbyrepetition.ui.flashcards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.newActivities.AddFlashcardActivity
import com.example.learnbyrepetition.newActivities.DetailsFlashcardActivity

class FlashcardSimpleAdapter(var flashcards: List<Flashcard>, context: Context) :
    RecyclerView.Adapter<FlashcardSimpleAdapter.FlashcardSimpleViewHolder>() {

    val mContext = context

    inner class FlashcardSimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
                val englishTextView: TextView
                val polishTextView: TextView

                var id: Long

                init {
                    englishTextView = itemView.findViewById(R.id.item_flashcard_simple_english)
                    polishTextView = itemView.findViewById(R.id.item_flashcard_simple_polish)

                    id = -1

                    itemView.setOnClickListener(this)
                }

        override fun onClick(p0: View?) {
            val intent = Intent(mContext, DetailsFlashcardActivity::class.java)

            var bundle = Bundle()
            bundle.putLong(mContext.getString(R.string.bundle_selected_flashcard_id), id)

            intent.putExtras(bundle)

            mContext.startActivity(intent)
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