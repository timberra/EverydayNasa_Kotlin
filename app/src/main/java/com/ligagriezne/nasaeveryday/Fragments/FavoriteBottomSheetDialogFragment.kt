package com.ligagriezne.nasaeveryday.Fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ligagriezne.nasaeveryday.DailyPost
import com.ligagriezne.nasaeveryday.R
import com.ligagriezne.nasaeveryday.databinding.FragmentTodayBinding
import com.ligagriezne.nasaeveryday.fromBundle

class FavoriteBottomSheetDialogFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayBinding.inflate(inflater)
        return binding.root
    }

    private fun Resources.calcDialogHeight(): Int =
        displayMetrics.heightPixels - getDimensionPixelOffset(R.dimen.bottom_sheet_dialog_fragment)

    override fun onStart() {
        super.onStart()
        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let {
            BottomSheetBehavior.from(it).apply {
                peekHeight = resources.calcDialogHeight()
                isFitToContents = true
            }
        }
    }

    private fun View.updateHeight(height: Int) {
        layoutParams = layoutParams.apply {
            this.height = height
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.updateHeight(resources.calcDialogHeight()) // Give a const height
        val args = arguments ?: return

        DailyPostViewHolder(binding).bind(
            DailyPostViewModel(
                post = DailyPost.fromBundle(args),
                isFavoriteEnabled = false
            )
        )
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