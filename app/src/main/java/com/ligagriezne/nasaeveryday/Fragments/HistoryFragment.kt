package com.ligagriezne.nasaeveryday.Fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.ligagriezne.nasaeveryday.NasaDataModel
import com.ligagriezne.nasaeveryday.NasaRepositoryImpl
import com.ligagriezne.nasaeveryday.R
import com.ligagriezne.nasaeveryday.Result
import com.ligagriezne.nasaeveryday.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryFragment : Fragment() {
    private var selectedDate: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val history: View = inflater.inflate(R.layout.fragment_history, container, false)
        val calendarView = history.findViewById<CalendarView>(R.id.calendar)
        val dateText = history.findViewById<TextView>(R.id.dateTextView)
        val findButton = history.findViewById<Button>(R.id.buttonFind)

        dateText.text = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth).format(DateTimeFormatter.ISO_DATE)
            dateText.text = selectedDate
        }

        findButton.setOnClickListener {
            // Call method to fetch data based on selectedDate
            fetchData(selectedDate)
        }

        return history
    }

    private fun fetchData(selectedDate: String?) {
        // Ensure selectedDate is not null before making the API call
        selectedDate?.let {
            // Make API call to fetch data for the selected date
            GlobalScope.launch(Dispatchers.Main) {
                val result: Result<NasaDataModel> = NasaRepositoryImpl.getPostByDate(selectedDate)
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: Result<NasaDataModel>) {
        if (result is Success) {
            // Data fetched successfully
            val data = result.data
            // Pass fetched data to bottom sheet dialog
            showBottomSheetDialog(data.title, data.date, data.url, data.explanation)
        } else {
            // Handle error
            // You might want to display an error message to the user
        }
    }

    private fun showBottomSheetDialog(title: String, date: String, url: String, explanation: String) {
        val bottomSheetFragment = HistoryBottomSheetDialogFragment.newInstance(title, date, url, explanation)
        bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
