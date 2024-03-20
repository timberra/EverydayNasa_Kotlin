package com.ligagriezne.nasaeveryday.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ligagriezne.nasaeveryday.DailyPost
import com.ligagriezne.nasaeveryday.DailyPostDatabase
import com.ligagriezne.nasaeveryday.R

class HistoryBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history_pop, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Retrieve arguments
        val title = arguments?.getString("title")
        val date = arguments?.getString("date")
        val url = arguments?.getString("url")
        val explanation = arguments?.getString("explanation")

        // Populate views with retrieved data
        view.findViewById<TextView>(R.id.titleText).text = title
        view.findViewById<TextView>(R.id.todayDate).text = date
        view.findViewById<TextView>(R.id.description).text = explanation

        // Load image into ImageView using custom extension function
        url?.let { view.findViewById<ImageView>(R.id.imageView).loadImage(it) }

        val addButton = view.findViewById<ImageButton>(R.id.favoriteButton)
        addButton.setOnClickListener {
            saveFavoriteToSharedPreferences()
        }
    }

    private fun saveFavoriteToSharedPreferences() {
        val favoritesDb = DailyPostDatabase(requireContext())
        favoritesDb.saveToFavorites(
            DailyPost(
                arguments?.getString("title").toString(),
                arguments?.getString("date").toString(),
                arguments?.getString("url").toString(),
                arguments?.getString("explanation").toString()
            )
        )
        Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
    }


    companion object {
        @JvmStatic
        fun newInstance(title: String, date: String, url: String, description: String) =
            HistoryBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("date", date)
                    putString("url", url)
                    putString("explanation", description)
                }
            }
    }
}