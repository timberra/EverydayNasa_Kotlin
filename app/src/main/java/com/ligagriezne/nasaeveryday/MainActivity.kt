package com.ligagriezne.nasaeveryday

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ligagriezne.nasaeveryday.Fragments.FavoriteFragment
import com.ligagriezne.nasaeveryday.Fragments.HistoryFragment
import com.ligagriezne.nasaeveryday.Fragments.TodayFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private var data: NasaDataModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reloadData()

        val favoriteFragment = FavoriteFragment.newInstance("", "")
        val historyFragment = HistoryFragment.newInstance("", "")

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.today -> makeCurrentFragment(
                    TodayFragment.newInstance(
                        data?.title ?: "Title not found",
                        data?.date ?: "Date not found",
                        data?.explanation ?: "Description not found",
                        data?.url ?: "N"
                    )
                )

                R.id.history -> makeCurrentFragment(historyFragment)
                R.id.favorite -> makeCurrentFragment(favoriteFragment)
            }
            true
        }

        makeCurrentFragment(TodayFragment.newInstance("", "", "", ""))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun reloadData() {
        val key = "tw3zjqcAbhFOy6J2qxf5ex1n4AthyXK2ssAHt5Us" // Replace with the desired post ID
        val call = ApiClient.apiService.getPostByDate(key, LocalDate.now().format(DateTimeFormatter.ISO_DATE))

        call.enqueue(object : Callback<NasaDataModel> {
            override fun onResponse(call: Call<NasaDataModel>, response: Response<NasaDataModel>) {
                if (response.isSuccessful) {
                    data = response.body()
                    // Handle the retrieved post data
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<NasaDataModel>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }

}

