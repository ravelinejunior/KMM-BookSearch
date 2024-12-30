package com.plcoding.bookpedia.book.data.datasource

import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.core.data.safeCall
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val BASE_URL = "https://openlibrary.org"
class KtorRemoteBookDataSource (
    private val httpClient: HttpClient
){
    suspend fun searchBooks(
        query:String,
        resultLimit:Int?= null
    ):Result<List<Book>,DataError.Remote>{
        return safeCall {
            httpClient.get("$BASE_URL/search.json?q=$query&limit=$resultLimit")
        }
    }
}