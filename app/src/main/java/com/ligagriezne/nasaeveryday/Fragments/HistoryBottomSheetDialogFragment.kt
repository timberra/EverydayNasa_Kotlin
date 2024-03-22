package com.ligagriezne.nasaeveryday.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ligagriezne.nasaeveryday.DailyPost
import com.ligagriezne.nasaeveryday.DailyPostDatabase
import com.ligagriezne.nasaeveryday.databinding.FragmentTodayBinding
import com.ligagriezne.nasaeveryday.fromBundle

class HistoryBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments ?: return
        DailyPostViewHolder(binding).bind(
            DailyPostViewModel(
                post = DailyPost.fromBundle(args),
                isFavoriteEnabled = true,
                onFavoriteClick = {
                    saveFavoriteToSharedPreferences()
                }
            )
        )
    }

    private fun saveFavoriteToSharedPreferences() {
        val favoritesDb = DailyPostDatabase(requireContext())
        val args = arguments ?: return
        favoritesDb.saveToFavorites(DailyPost.fromBundle(args))
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