package com.example.airvibes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airvibes.databinding.ActivityAviationNewsBinding

class AviationNews : AppCompatActivity() {
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding:ActivityAviationNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAviationNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        recyclerView = findViewById(R.id.newsreclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

      binding.backbtnlist.setOnClickListener {
          onBackPressed()
      }
        newsViewModel.newsList.observe(this, Observer { articles ->
            newsAdapter = NewsAdapter(articles)
            recyclerView.adapter = newsAdapter
        })


        newsViewModel.fetchNews("india-aviation-airlines")
    }
}