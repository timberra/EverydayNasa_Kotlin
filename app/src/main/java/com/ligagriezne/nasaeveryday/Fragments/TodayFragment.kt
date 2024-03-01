package com.ligagriezne.nasaeveryday.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ligagriezne.nasaeveryday.databinding.FragmentTodayBinding
import androidx.fragment.app.viewModels

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
//            binding.imageView.loadImageWithGlide(post.imageUrl)
            binding.todayDate.text = post.date
            binding.description.text = post.description
        }
        viewModel.showToast.observe(viewLifecycleOwner) { message ->
            Toast
                .makeText(context, message, Toast.LENGTH_LONG)
                .show()
        }
        binding.titleText.setOnClickListener { viewModel.onTitleClick() }
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

