package com.smi.giphy.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "url") val url: String = "",
)