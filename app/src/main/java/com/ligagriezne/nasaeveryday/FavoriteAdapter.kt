package com.ligagriezne.nasaeveryday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(private var favorites: List<String>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    fun updateData(newFavorites: List<String>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val favoriteTextView: TextView = itemView.findViewById(R.id.favoriteRecyclerView)

        fun bind(favorite: String) {
            favoriteTextView.text = favorite
        }
    }
}
