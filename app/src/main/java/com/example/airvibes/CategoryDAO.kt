package com.example.airvibes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)



    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryById(categoryId: Long): Category?


    @Query("SELECT * FROM categories WHERE cname = :categoryName LIMIT 1")
    fun getCategoryByName(categoryName: String): Category?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCount(): Int
}
