package com.ligagriezne.nasaeveryday.Fragments

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.findViewById(R.id.favoriteRecyclerView)
        adapter = FavoriteAdapter(emptyList()) // Initialize adapter with an empty list
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoriteItems = getSavedFavorites()
            .map { item -> FavoriteItem(title = item.title, date = "") }
        adapter.updateData(favoriteItems) // Update adapter data with saved favorites
//        adapter = FavoriteAdapter(requireActivity())
//        recyclerView.adapter = adapter

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
                            R.color.layoutBackround
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
        favoritesDb.deleteFromFavorites(DailyPost(deletedItem.title))
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

