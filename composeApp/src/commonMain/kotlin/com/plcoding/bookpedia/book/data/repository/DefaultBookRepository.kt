package com.plcoding.bookpedia.book.data.repository

import com.plcoding.bookpedia.book.data.datasource.RemoteBookDataSource
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.book.domain.repository.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
):BookRepository {
    override suspend fun searchBooks(
        query: String
    ): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map {
                    it.toBook()
                }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote> {
        return  remoteBookDataSource
            .getBookDetails(bookId)
            .map { it.description }
    }
}