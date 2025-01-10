package com.example.airvibes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel : ViewModel() {

    private val _newsList = MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> get() = _newsList

    private val newsApiService = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApiService::class.java)

    private val apiKey = "8785c2cf70f7430bb435520cbc2407fb"

    fun fetchNews(query: String) {
        viewModelScope.launch {
            try {
                val response = newsApiService.getNews(query, apiKey)
                if (response.isSuccessful) {
                    response.body()?.articles?.let {
                        _newsList.postValue(it)
                    }
                }
            } catch (e: Exception) {
                // Handle error (e.g., network failure)
                e.printStackTrace()
            }
        }
    }
}
