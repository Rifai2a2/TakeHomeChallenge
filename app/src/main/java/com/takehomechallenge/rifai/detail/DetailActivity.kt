package com.takehomechallenge.rifai.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.takehomechallenge.rifai.database.FavoriteEntity
import com.takehomechallenge.rifai.R
import com.takehomechallenge.rifai.response.ResultsItem
import com.takehomechallenge.rifai.ViewModelFactory
import com.takehomechallenge.rifai.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var favoriteStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detail = intent.getParcelableExtra<ResultsItem>(DETAIL_ACTIVITY)
        if (detail != null) {
            setupAction(detail)
            setupFavoriteAction(detail)
        }
        supportActionBar?.hide()
    }

    private fun setupAction(detail: ResultsItem){
        Glide.with(applicationContext)
            .load(detail.image)
            .into(binding.imageView)
        binding.nameTextView.text = detail.name
        binding.speciesTextView.text = detail.species
        binding.genderTextView.text = detail.gender
        binding.locationTextView.text = detail.location.toString()
        binding.originTextView.text = detail.origin.toString()
    }

    private fun setupFavoriteAction(detail: ResultsItem) {
        binding.ivFavorite.setOnClickListener {
            val username = detail.name
            val favoriteUser = FavoriteEntity(username,detail.image, detail.species, detail.gender,
                detail.location.toString(),
                detail.origin.toString()
            )
            if (favoriteStatus) {
                detailViewModel.deleteFavoriteUser(favoriteUser)
                Toast.makeText(
                    this,
                    getString(R.string.favorite_dihapus),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                detailViewModel.addFavoriteUser(favoriteUser)
                Toast.makeText(
                    this,
                    getString(R.string.favorite_ditambahkan),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val username = detail.name
        detailViewModel.getFavoriteUserByUsername(username).observe(this) { favUser ->
            favoriteStatus = if (favUser != null) {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
                true
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                false
            }
        }
    }

    companion object {
        const val DETAIL_ACTIVITY = "detail_activity"
    }
}