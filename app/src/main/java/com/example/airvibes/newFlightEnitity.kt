package com.example.airvibes



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "webcategoriess", indices = [Index(value = ["wnames", "wimage_urls","prices","booklink"], unique = true)])
data class WebCategory(
    @PrimaryKey(autoGenerate = true) val idws: Long = 0,
    @ColumnInfo(name = "wnames") val wname: String,
    @ColumnInfo(name = "wimage_urls") val wimageUrl: String,
    @ColumnInfo(name="prices") val prices:String,
    @ColumnInfo(name="booklink") val booklink:String
)
