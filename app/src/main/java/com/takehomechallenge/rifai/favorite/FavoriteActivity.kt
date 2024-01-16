package com.takehomechallenge.rifai.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.takehomechallenge.rifai.response.Location
import com.takehomechallenge.rifai.response.Origin
import com.takehomechallenge.rifai.response.ResultsItem
import com.takehomechallenge.rifai.ViewModelFactory
import com.takehomechallenge.rifai.adapter.UserAdapter
import com.takehomechallenge.rifai.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : UserAdapter

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoriteViewModel.getAllFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<ResultsItem>()
            users.map {
                val origin = Origin(name = it.origin, url = "")
                val location = Location(name = it.location, url = "")
                val item = ResultsItem( image = it.image, name = it.name, species = it.species, gender = it.gender, origin = origin, location = location)
                items.add(item)
            }
            adapter.submitList(items)


        }
        binding.rvFavorite.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}