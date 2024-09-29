package com.example.netology.domain

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.netology.model.GameStatistics
import com.example.netology.data.GameStatisticsDbHelper

class GameStatisticsRepository(context: Context) {
    private val dbHelper = GameStatisticsDbHelper(context)

    fun insert(statistics: GameStatistics) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("totalClicks", statistics.totalClicks)
            put("mouseClicks", statistics.mouseClicks)
            put("hitPercentage", statistics.hitPercentage)
            put("gameDuration", statistics.gameDuration)
        }
        db.insert("game_statistics", null, values)
    }

    fun getLastGames(): List<GameStatistics> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "game_statistics",
            null,
            null,
            null,
            null,
            null,
            "id DESC",
            "10"
        )

        val statistics = mutableListOf<GameStatistics>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("id"))
                val totalClicks = getInt(getColumnIndexOrThrow("totalClicks"))
                val mouseClicks = getInt(getColumnIndexOrThrow("mouseClicks"))
                val hitPercentage = getFloat(getColumnIndexOrThrow("hitPercentage"))
                val gameDuration = getLong(getColumnIndexOrThrow("gameDuration"))
                statistics.add(GameStatistics(id, totalClicks, mouseClicks, hitPercentage, gameDuration))
            }
        }
        cursor.close()
        return statistics
    }
}
