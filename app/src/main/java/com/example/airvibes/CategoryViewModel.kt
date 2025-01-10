package com.example.airvibes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val categoryDao = AppDatabase.getDatabase(application).categoryDao()

    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()

    suspend fun isDatabasePopulated(): Boolean {
        return categoryDao.getCount() > 0
    }

    fun insertAll(categories: List<Category>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (category in categories) {
                if (categoryDao.getCategoryById(category.id) == null) {
                    categoryDao.insert(category)
                }
            }
        }
    }
}
