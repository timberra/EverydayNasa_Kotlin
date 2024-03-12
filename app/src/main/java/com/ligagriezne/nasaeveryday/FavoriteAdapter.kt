package com.ligagriezne.nasaeveryday

import FavoriteBottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(private var favorites: List<FavoriteItem>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    // Function to update the data in the adapter
    fun updateData(newFavorites: List<FavoriteItem>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.favoriteDateTextView)
        private val titleTextView: TextView = itemView.findViewById(R.id.favoriteTitleTextView)
        private val context = itemView.context

        init {
            // Set click listener for the item view (cell)
            itemView.setOnClickListener {
                // Create and show the bottom sheet dialog
                val bottomSheetDialogFragment = FavoriteBottomSheetDialogFragment()
                bottomSheetDialogFragment.show((context as AppCompatActivity).supportFragmentManager, bottomSheetDialogFragment.tag)
            }
        }

        fun bind(favoriteItem: FavoriteItem) {
            dateTextView.text = favoriteItem.date
            titleTextView.text = favoriteItem.title
        }
    }

    fun removeItem(position: Int): FavoriteItem {
        val deletedItem = favorites[position]
        favorites = favorites.toMutableList().apply { removeAt(position) }
        notifyItemRemoved(position)
        return deletedItem
    }
}