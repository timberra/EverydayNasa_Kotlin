package com.ligagriezne.nasaeveryday.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ligagriezne.nasaeveryday.DailyPost
import com.ligagriezne.nasaeveryday.DailyPostDatabase
import com.ligagriezne.nasaeveryday.databinding.FragmentTodayBinding


class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding
    private val viewModel: TodayScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodayBinding.inflate(inflater)
        return binding.root
    }

    // Called after the view is created or resumed from background.
    override fun onResume() {
        super.onResume()
        viewModel.bind()
        viewModel.todayPost.observe(viewLifecycleOwner) { post ->
            binding.titleText.text = post.title
            binding.imageView.loadImage(post.imageUrl)
            binding.todayDate.text = post.date
            binding.description.text = post.description
            binding.favoriteButton.setOnClickListener {
                // Save the current post to favorites
                saveFavoriteToSharedPreferences(requireContext(), post)
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe showToast LiveData to display toast messages
        viewModel.showToast.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        // Set OnClickListener for the title text
        binding.titleText.setOnClickListener { viewModel.onTitleClick() }
    }

    // Function to save post to SharedPreferences as a favorite
    private fun saveFavoriteToSharedPreferences(context: Context, post: TodayPostViewModel) {
        val favoritesDb = DailyPostDatabase(context)
        favoritesDb.saveToFavorites(DailyPost(post.title))
    }




    // Called when the view is about to go in background or gets destroyed.
    override fun onPause() {
        super.onPause()
        viewModel.unbind()
    }

    companion object {
        @JvmStatic
        fun newInstance() = TodayFragment()
    }
}

