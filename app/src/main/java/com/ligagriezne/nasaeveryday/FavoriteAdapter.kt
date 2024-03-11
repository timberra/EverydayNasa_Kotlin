package com.ligagriezne.nasaeveryday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
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

        fun bind(favoriteItem: FavoriteItem) {
            dateTextView.text = favoriteItem.date
            titleTextView.text = favoriteItem.title

            itemView.setOnClickListener {
                // Inflate the popup view
                val inflater = LayoutInflater.from(itemView.context)
                val popupView = inflater.inflate(R.layout.fragment_favorit_pop, null)

                // Set the content of the popup view
//                val popupDateTextView: TextView = popupView.findViewById(R.id.popupDateTextView)
                val popupTitleTextView: TextView = popupView.findViewById(R.id.titleText)
//                popupDateTextView.text = favoriteItem.date
                popupTitleTextView.text = favoriteItem.title

                // Create the popup window
                val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

                // Show the popup window
                popupWindow.showAsDropDown(itemView)
            }
        }
    }
    fun removeItem(position: Int): FavoriteItem {
        val deletedItem = favorites[position]
        favorites = favorites.toMutableList().apply { removeAt(position) }
        notifyItemRemoved(position)
        return deletedItem
    }
}