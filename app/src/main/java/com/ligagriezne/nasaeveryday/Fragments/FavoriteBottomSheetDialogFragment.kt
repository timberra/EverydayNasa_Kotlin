package com.ligagriezne.nasaeveryday.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ligagriezne.nasaeveryday.R

class FavoriteBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorit_pop, container, false)
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
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, date: String, url: String, description: String) =
            FavoriteBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("date", date)
                    putString("url", url)
                    putString("explanation", description)
                }
            }
    }
}