package com.ligagriezne.nasaeveryday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                // Handle item click, show pop-up window with full view of the item
            }
        }
    }
}


//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class FavoriteAdapter(private var favorites: List<NasaDataModel>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
//
//    fun updateData(newFavorites: List<NasaDataModel>) {
//        favorites = newFavorites
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_cell, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(favorites[position])
//    }
//
//    override fun getItemCount(): Int {
//        return favorites.size
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val dateTextView: TextView = itemView.findViewById(R.id.favoriteDateTextView)
//        private val titleTextView: TextView = itemView.findViewById(R.id.favoriteTitleTextView)
//
//        fun bind(nasaDataModel: NasaDataModel) {
//            dateTextView.text = nasaDataModel.date
//            titleTextView.text = nasaDataModel.title
//        }
//    }
//}