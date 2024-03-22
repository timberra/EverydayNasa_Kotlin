package com.ligagriezne.nasaeveryday

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ligagriezne.nasaeveryday.Fragments.FavoriteBottomSheetDialogFragment
import com.ligagriezne.nasaeveryday.databinding.FavoriteCellBinding

class FavoriteAdapter(private var favorites: List<FavoriteItem>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    // Function to update the data in the adapter
    fun updateData(newFavorites: List<FavoriteItem>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FavoriteCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    inner class ViewHolder(private val binding: FavoriteCellBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(favoriteItem: FavoriteItem) {
            itemView.setOnClickListener {
                // Create and show the bottom sheet dialog
                val bottomSheetDialogFragment = FavoriteBottomSheetDialogFragment.newInstance(
                    favoriteItem.title,
                    favoriteItem.date,
                    favoriteItem.url,
                    favoriteItem.explanation
                )
                bottomSheetDialogFragment.show(
                    (context as AppCompatActivity).supportFragmentManager,
                    bottomSheetDialogFragment.tag
                )
            }
            binding.favoriteTitleTextView.text = favoriteItem.title
            binding.favoriteDateTextView.text = favoriteItem.date
        }

    }

    fun removeItem(position: Int): FavoriteItem {
        val deletedItem = favorites[position]
        favorites = favorites.toMutableList().apply { removeAt(position) }
        notifyItemRemoved(position)
        return deletedItem
    }
}