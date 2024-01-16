package com.takehomechallenge.rifai.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.takehomechallenge.rifai.detail.DetailActivity
import com.takehomechallenge.rifai.databinding.ItemUserBinding
import com.takehomechallenge.rifai.response.ResultsItem

class UserAdapter  : ListAdapter<ResultsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var filteredList: List<ResultsItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun setFilteredList(list: List<ResultsItem>) {
        filteredList = list
        submitList(filteredList)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem){
            binding.name.text = item.name
            binding.species.text = item.species
            Glide.with(itemView.context)
                .load(item.image)
                .into(binding.image)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DETAIL_ACTIVITY, item)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}