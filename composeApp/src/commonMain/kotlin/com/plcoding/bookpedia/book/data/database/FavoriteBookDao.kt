package com.plcoding.bookpedia.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Upsert
    suspend fun upsert(bookEntity: BookEntity)

    @Query("SELECT * FROM favorite_books_table ORDER BY id DESC")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM favorite_books_table WHERE id = :id")
    fun getFavoriteBook(id: String): BookEntity?

    @Query("DELETE FROM favorite_books_table WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String)
}