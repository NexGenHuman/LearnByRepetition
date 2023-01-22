package com.example.learnbyrepetition

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.learnbyrepetition.classes.Flashcard


/*
private const val DATABASE_NAME: String = "FLASHCARDS_DATABASE"
private const val DATABASE_VERSION: Int = 1


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        var query = StringBuilder()

        query.append("CREATE TABLE " + Flashcards.TABLE_NAME + " (")
        query.append(Flashcards.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, ")
        query.append(Flashcards.ENGLISH_COL + " TEXT,")
        query.append(Flashcards.POLISH_COL + " TEXT,")
        query.append(Flashcards.LAST_STUDIED_COL + " INTEGER,")
        query.append(Flashcards.SUCCESS_COUNT + " INTEGER,")
        query.append(Flashcards.FAILURE_COUNT + " INTEGER,")
        query.append(Flashcards.IS_WORD + " INTEGER DEFAULT 0,")
        query.append(Flashcards.IS_DATA_DEFAULT + " INTEGER DEFAULT 0)")

        db.execSQL(query.toString())
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + Flashcards.TABLE_NAME)
        onCreate(db)
    }

    fun addFlashcard(flashcard: Flashcard){
        val values = ContentValues()

        values.put()
        values.put()
        values.put()
        values.put()
        values.put()
        values.put()
        values.put()
    }

    fun getFlashcard(id: Int): Flashcard{
        TODO()
    }

    fun getFlashcardList(id: Int): List<Flashcard>{
        TODO()
    }

    companion object Flashcards {

        const val TABLE_NAME: String = "FLASHCARDS"
        const val ID_COL: String = "ID"
        const val ENGLISH_COL: String = "ENGLISH"
        const val POLISH_COL: String = "POLISH"
        const val LAST_STUDIED_COL: String = "LAST_STUDIED"
        const val SUCCESS_COUNT: String = "SUCCESS_COUNT"
        const val FAILURE_COUNT: String = "FAILURE_COUNT"
        const val IS_WORD: String = "IS_WORD"
        const val IS_DATA_DEFAULT: String = "IS_DATA_DEFAULT"
    }

}*/
