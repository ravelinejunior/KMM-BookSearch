package com.plcoding.bookpedia.book.presentation.book_detail

import com.plcoding.bookpedia.book.domain.model.Book

data class BookDetailState(
    val selectedBook:Book,
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false
)
