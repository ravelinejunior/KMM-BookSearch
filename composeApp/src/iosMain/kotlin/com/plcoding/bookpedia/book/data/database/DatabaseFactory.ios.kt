package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabase

actual class DatabaseFactory {
/*    actual fun create():RoomDatabase.Builder<FavoriteBookDatabase> {
        val databaseFile = documentDirectory()+"/${FavoriteBookDatabase.DATABASE_NAME}"
        return RoomDatabase.Builder<FavoriteBookDatabase>(
            name = databaseFile
        )
    }

    private fun documentDirectory():String{
        val documentDirectory = NSFileManager.defaultManager().URLsForDirectory(
            directory = NSDocumentDirectory,
            inDomains = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requitedNotNull(documentDirectory?.path())
    }*/
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        TODO("Not yet implemented")
    }
}