package com.example.learnbyrepetition.ui.flashcardSets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnbyrepetition.R
import com.example.learnbyrepetition.database.classes.FlashcardSet
import com.example.learnbyrepetition.newActivities.DetailsFlashcardSetActivity
import com.example.learnbyrepetition.ui.flashcards.FlashcardAdapter

class FlashcardSetAdapter(var flashcardSets: List<FlashcardSet>, context: Context) :
    RecyclerView.Adapter<FlashcardSetAdapter.FlashcardSetViewHolder>() {

    val mContext = context


    inner class FlashcardSetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var titleTextView: TextView
        var flashcardCountTextView: TextView

        var id: Long

        init {
            titleTextView = itemView.findViewById(R.id.item_flashcard_set_title)
            flashcardCountTextView = itemView.findViewById(R.id.item_flashcard_set_flashcard_count)

            id = -1

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            var intent = Intent(mContext, DetailsFlashcardSetActivity::class.java)

            var bundle = Bundle()
            bundle.putLong(mContext.getString(R.string.bundle_selected_flashcard_set_id), id)

            intent.putExtras(bundle)

            mContext.startActivity(intent)
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlashcardSetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard_set, parent, false)
        return FlashcardSetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flashcardSets.size
    }

    override fun onBindViewHolder(holder: FlashcardSetAdapter.FlashcardSetViewHolder, position: Int) {
        with(flashcardSets[position]) {
            holder.titleTextView.text = name
            holder.flashcardCountTextView.text = String.format("%d", -1)

            holder.id = id_set
        }
    }


}