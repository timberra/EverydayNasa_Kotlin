package com.ligagriezne.nasaeveryday.Fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ligagriezne.nasaeveryday.R

class FavoriteBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorit_pop, container, false)
        return view
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