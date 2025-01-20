package com.plcoding.bookpedia.book.presentation.book_detail

import com.plcoding.bookpedia.book.domain.model.Book

sealed interface BookDetailAction {
    data object OnBackClickAction : BookDetailAction
    data object OnFavoriteClickAction : BookDetailAction
    data class OnSelectedBookChangedAction(val selectedBook: Book) : BookDetailAction
}