package com.hitha.android

object FavoriteManager {
    private val favorites = mutableSetOf<Int>()

    fun isFavorite(userId: Int): Boolean = favorites.contains(userId)

    fun toggle(userId: Int): Boolean {
        return if (favorites.remove(userId)) {
            false
        } else {
            favorites.add(userId)
            true
        }
    }
}
