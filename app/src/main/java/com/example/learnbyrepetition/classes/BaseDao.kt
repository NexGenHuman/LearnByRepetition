package com.example.learnbyrepetition.classes

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    //Why is Room Insert return type Long and others Int? Couldn't find documentation for that :-(
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(obj: T): Long

    @Update
    suspend fun update(obj: T): Int

    @Delete
    suspend fun delete(obj: T): Int
}