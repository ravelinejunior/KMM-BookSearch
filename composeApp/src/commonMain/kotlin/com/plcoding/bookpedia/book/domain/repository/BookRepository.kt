package com.plcoding.bookpedia.book.domain.repository

import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(bookId: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book)
    suspend fun deleteFromFavorite(bookId: String)
}