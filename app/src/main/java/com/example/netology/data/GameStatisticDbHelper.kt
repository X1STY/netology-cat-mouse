package com.example.netology.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_NAME = "game_statistics.db"
private const val DATABASE_VERSION = 1

class GameStatisticsDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE game_statistics (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                totalClicks INTEGER,
                mouseClicks INTEGER,
                hitPercentage REAL,
                gameDuration INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS game_statistics")
        onCreate(db)
    }
}
