package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.model.Book

sealed interface BookListAction {
    data object RefreshAction : BookListAction
    data class OnSearchQueryAction(val query: String) : BookListAction
    data class OnBookClickAction(val book: Book) : BookListAction
    data class OnTabSelectedAction(val index: Int) : BookListAction
}