package com.ligagriezne.nasaeveryday

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

private const val FAVORITES_KEY = "favorites"

enum class SaveResult {
    SUCCESS, FAILED, ALREADY_EXIST
}

class DailyPostDatabase(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    fun saveToFavorites(post: DailyPost): SaveResult {
        val allPosts = getAllFavoritePosts()
        if (allPosts.any { item -> item.title == post.title }) return SaveResult.ALREADY_EXIST

        allPosts.add(post)
        val allPostsString = Gson().toJson(allPosts)
        sharedPreferences.edit().putString(FAVORITES_KEY, allPostsString).apply()
        return SaveResult.SUCCESS
    }

    fun getAllFavoritePosts(): MutableList<DailyPost> {
        // JSON [{"title": "Post title of the day", "description": "asdhjfhksjd"}, {"title": "Post title of the day"}]
        val favoritesString = sharedPreferences.getString(FAVORITES_KEY, "")
        // Turn string back into the list of objets
        val result: MutableList<DailyPost> = try {
            Gson().fromJson(
                favoritesString, object : TypeToken<MutableList<DailyPost>>() {}.type
            )
        } catch (e: JsonSyntaxException) {
            mutableListOf()
        } catch (e: NullPointerException) {
            mutableListOf()
        }
        return result
    }

}

data class DailyPost(
    val title: String
)

