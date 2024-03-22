package com.ligagriezne.nasaeveryday.Fragments

import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ligagriezne.nasaeveryday.DailyPost
import com.ligagriezne.nasaeveryday.DailyPostDatabase
import com.ligagriezne.nasaeveryday.FavoriteAdapter
import com.ligagriezne.nasaeveryday.FavoriteItem
import com.ligagriezne.nasaeveryday.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

private const val SORT_DESCENDING = 0

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private lateinit var sortSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.findViewById(R.id.favoriteRecyclerView)
        sortSpinner = view.findViewById(R.id.sortSpinner)

        adapter = FavoriteAdapter(emptyList()) // Initialize adapter with an empty list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize spinner with sorting options
        val sortingOptions = arrayOf("Newest to Oldest", "Oldest to Newest")
        val spinnerAdapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            sortingOptions
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.typeface = Typeface.MONOSPACE
                return view
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.typeface = Typeface.MONOSPACE
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.adapter = spinnerAdapter

        // Set listener for spinner item selection
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Update RecyclerView data based on selected sorting option
                val favorites = getSavedFavorites()
                val sortedFavorites = if (position == SORT_DESCENDING) {
                    favorites.sortedByDescending { it.date }
                } else {
                    favorites.sortedBy { it.date }
                }
                val favoriteItems = sortedFavorites.map { item ->
                    FavoriteItem(
                        title = item.title,
                        date = item.date ?: "",
                        url = item.url ?: "",
                        explanation = item.explanation ?: ""
                    )
                }
                adapter.updateData(favoriteItems)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Swipe to delete functionality
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val deletedItem = adapter.removeItem(position)

                deleteItemFromDatabase(deletedItem)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.layoutBackground
                        )
                    )
                    .addActionIcon(R.drawable.delete_icon)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    // Function to delete item from the database
    private fun deleteItemFromDatabase(deletedItem: FavoriteItem) {
        val favoritesDb = DailyPostDatabase(requireContext())
        val removablePost = DailyPost(
            deletedItem.title, deletedItem.date, deletedItem.url, deletedItem.explanation
        )
        favoritesDb.deleteFromFavorites(removablePost)
    }

    private fun getSavedFavorites(): List<DailyPost> {
        val favoritesDb = DailyPostDatabase(requireContext())
        return favoritesDb.getAllFavoritePosts()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
