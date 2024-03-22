package com.ligagriezne.nasaeveryday

import android.content.Context
import android.os.Bundle
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

    fun deleteFromFavorites(post: DailyPost) {
        val allPosts = getAllFavoritePosts()
        allPosts.remove(allPosts.find { it.title == post.title })
        val allPostsString = Gson().toJson(allPosts)
        sharedPreferences.edit().putString(FAVORITES_KEY, allPostsString).apply()
    }
}

data class DailyPost(
    val title: String,
    val date: String,
    val url: String,
    val explanation: String
) {
    companion object
}


private const val DAILY_POST_TITLE = "title"
private const val DAILY_POST_DATE = "date"
private const val DAILY_POST_URL = "url"
private const val DAILY_POST_DESCRIPTION = "explanation"

fun DailyPost.toBundle() = Bundle().apply {
    putString(DAILY_POST_TITLE, title)
    putString(DAILY_POST_DATE, date)
    putString(DAILY_POST_URL, url)
    putString(DAILY_POST_DESCRIPTION, explanation)
}

fun DailyPost.Companion.fromBundle(bundle: Bundle) = bundle.let { arguments ->
    DailyPost(
        title = arguments.getString(DAILY_POST_TITLE, ""),
        date = arguments.getString(DAILY_POST_DATE, ""),
        url = arguments.getString(DAILY_POST_URL, ""),
        explanation = arguments.getString(DAILY_POST_DESCRIPTION, "")
    )
}

