package com.example.learnbyrepetition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnbyrepetition.database.classes.Flashcard
import com.example.learnbyrepetition.newActivities.DetailsFlashcardActivity
import java.text.SimpleDateFormat

class FlashcardAdapter(var flashcards: List<Flashcard>, context: Context) :
    RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder>() {

    val mContext = context

    inner class FlashcardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val titleTextView: TextView
        val ratioTextView: TextView
        val lastStudiedTextView: TextView

        var id: Long

        init {
            titleTextView = itemView.findViewById(R.id.item_flashcard_title)
            ratioTextView = itemView.findViewById(R.id.item_flashcard_fail_success_ratio)
            lastStudiedTextView = itemView.findViewById(R.id.item_flashcard_last_studied)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_flashcard, parent, false)
        return FlashcardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")

        with(flashcards[position]) {
            holder.titleTextView.text = englishText
            holder.ratioTextView.text = "Success $successCount/$failureCount Failures"

            if (dateLastStudied == null)
                holder.lastStudiedTextView.text = mContext.getString(R.string.never_studied)
            else
                holder.lastStudiedTextView.text = dateFormat.format(dateLastStudied)

            holder.id = id_flashcard
        }
    }
}