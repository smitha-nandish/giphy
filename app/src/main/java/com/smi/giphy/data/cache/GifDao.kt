package com.smi.giphy.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GifDao {

    /**
     * Call getSavedGifs() to get all gifs in the database
     */
    @Query("SELECT * FROM gifentity")
    fun getSavedGifs(): List<GifEntity>

    /**
     * Call insertAll() to save gif to the database
     */
    @Insert
    fun insertAll(vararg gifs: GifEntity)


    /**
     * Call delete() to save gif to the database
     */
    @Delete
    fun delete(gif: GifEntity)
}