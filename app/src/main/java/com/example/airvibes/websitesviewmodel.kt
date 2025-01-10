package com.example.airvibes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WebCategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val webcategoryDao = AppDatabase.getDatabase(application).webCategoryDao()

    val allCategories: LiveData<List<WebCategory>> = webcategoryDao.getAllCategories()
    val allCategoriesSortedByPrice: LiveData<List<WebCategory>> = webcategoryDao.getAllCategoriesSortedByPrice()
    suspend fun isDatabasePopulated(): Boolean {
        return webcategoryDao.getCount() > 0
    }

    fun insertAll(categories: List<WebCategory>) {
        viewModelScope.launch(Dispatchers.IO) {
            for (category in categories) {
                if (webcategoryDao.getCategoryById(category.idws) == null) {
                    webcategoryDao.insert(category)
                }
            }
        }
    }


    fun insertCategory(category: WebCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            if (webcategoryDao.getCategoryById(category.idws) == null) {
                webcategoryDao.insert(category)
            }
        }
    }

}