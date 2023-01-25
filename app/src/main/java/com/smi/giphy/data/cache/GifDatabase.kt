package com.smi.giphy.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GifEntity::class], version = 1, exportSchema = true)
abstract class GifDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}
