package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.model.Book

data class BookListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "Harry Potter",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val selectedTabIndex: Int = 0,
    val errorMessage: String? = null
)
