package com.example.airvibes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WebsitesDao {
    @Query("SELECT * FROM webcategoriess")
    fun getAllCategories(): LiveData<List<WebCategory>>
    @Query("SELECT * FROM webcategoriess ORDER BY prices ASC")
    fun getAllCategoriesSortedByPrice(): LiveData<List<WebCategory>>


    @Update
    suspend fun update(category: WebCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: WebCategory)



    @Query("SELECT * FROM webcategoriess WHERE idws = :categoryId")
    fun getCategoryById(categoryId: Long): WebCategory?


    @Query("SELECT * FROM webcategoriess WHERE wnames = :categoryName LIMIT 1")
    fun getCategoryByName(categoryName: String): WebCategory?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(webcategoriess: List<WebCategory>)

    @Query("SELECT COUNT(*) FROM webcategoriess")
    suspend fun getCount(): Int
}
