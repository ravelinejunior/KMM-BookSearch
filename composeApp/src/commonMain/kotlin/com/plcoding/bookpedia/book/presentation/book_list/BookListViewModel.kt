package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClickAction -> {}
            is BookListAction.OnTabSelectedAction -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = action.index,
                        isRefreshing = false,
                        isLoading = false,
                    )
                }
            }

            is BookListAction.OnSearchQueryAction -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query,
                        isRefreshing = false,
                        isLoading = false,
                    )
                }
            }

            is BookListAction.RefreshAction -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isRefreshing = true,
                    )
                }
            }
        }
    }
}