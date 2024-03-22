package com.ligagriezne.nasaeveryday.Fragments

import com.ligagriezne.nasaeveryday.DailyPost
import com.ligagriezne.nasaeveryday.databinding.FragmentTodayBinding
import com.ligagriezne.nasaeveryday.setVisibleOrGone

class DailyPostViewHolder(private val binding: FragmentTodayBinding) {

    fun bind(viewModel: DailyPostViewModel) {
        binding.titleText.text = viewModel.post.title
        binding.imageView.loadImage(viewModel.post.url)
        binding.todayDate.text = viewModel.post.date
        binding.description.text = viewModel.post.explanation
        binding.favoriteButton.setVisibleOrGone(viewModel.isFavoriteEnabled)
        binding.favoriteButton.setOnClickListener {
            viewModel.onFavoriteClick.invoke()
        }
    }
}


data class DailyPostViewModel(
    val post: DailyPost ,
    val isFavoriteEnabled: Boolean = true,
    val onFavoriteClick: () -> Unit = {}
)

fun TodayPostViewModel.toDailyPostViewModel(onFavoriteClick: () -> Unit) = DailyPostViewModel(
    post = DailyPost(title, date, imageUrl, description),
    onFavoriteClick = onFavoriteClick
)