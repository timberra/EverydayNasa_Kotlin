package com.ligagriezne.nasaeveryday.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ligagriezne.nasaeveryday.DailyPost
import com.ligagriezne.nasaeveryday.DailyPostDatabase
import com.ligagriezne.nasaeveryday.FavoriteAdapter
import com.ligagriezne.nasaeveryday.FavoriteItem
import com.ligagriezne.nasaeveryday.R

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.findViewById(R.id.favoriteRecyclerView)
        adapter = FavoriteAdapter(emptyList()) // Initialize adapter with an empty list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteItems = getSavedFavorites()
            .map { item -> FavoriteItem(title = item.title, date = "") }
        adapter.updateData(favoriteItems) // Update adapter data with saved favorites
    }


    private fun getSavedFavorites(): List<DailyPost> {
        val favoritesDb = DailyPostDatabase(requireContext())
        return favoritesDb.getAllFavoritePosts()
    }

//    private fun parseFavoriteItem(favoriteString: String): FavoriteItem? {
//        val parts = favoriteString.split("|")
//        return if (parts.size == 2) {
//            FavoriteItem(parts[0], parts[1])
//        } else {
//            null // Return null for invalid format
//        }
//    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}

