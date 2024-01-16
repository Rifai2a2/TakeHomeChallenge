package com.takehomechallenge.rifai.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehomechallenge.rifai.R
import com.takehomechallenge.rifai.adapter.UserAdapter
import com.takehomechallenge.rifai.databinding.ActivityMainBinding
import com.takehomechallenge.rifai.favorite.FavoriteActivity
import com.takehomechallenge.rifai.response.ResultsItem

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)


         adapter = UserAdapter()
        binding.rvReview.adapter = adapter

        mainViewModel.listReview.observe(this) { consumerReviews ->
            adapter.submitList(consumerReviews)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.favorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        mainViewModel.getUserData()
    }

    private fun filterList(query: String?) {
        val consumerReviews = mainViewModel.listReview.value

        if (consumerReviews != null) {
            if (!query.isNullOrBlank()) {
                val filteredList = consumerReviews.filter {
                    it!!.name.contains(query, ignoreCase = true)
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
                }

                adapter.setFilteredList(filteredList as List<ResultsItem>)
            } else {
                adapter.setFilteredList(consumerReviews as List<ResultsItem>)
            }
        } else {
            adapter.setFilteredList(emptyList())
        }
    }



    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}