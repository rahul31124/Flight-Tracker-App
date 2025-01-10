package com.example.airvibes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "webcategories", indices = [Index(value = ["wname", "wimage_url"], unique = true)])
data class WebsCategory(
    @PrimaryKey(autoGenerate = true) val idw: Long = 0,
    @ColumnInfo(name = "wname") val wname: String,
    @ColumnInfo(name = "wimage_url") val wimageUrl: String
)
