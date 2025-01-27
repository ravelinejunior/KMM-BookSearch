package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.routes.Route
import com.plcoding.bookpedia.book.domain.repository.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.onStart {
        fetchBookDescription()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnSelectedBookChangedAction -> {
                _state.update {
                    it.copy(
                        selectedBook = action.selectedBook
                    )
                }
            }

            is BookDetailAction.OnFavoriteClickAction -> {

            }

            else -> Unit
        }
    }

    private fun fetchBookDescription() = viewModelScope.launch {
        val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

        bookRepository.getBookDescription(bookId)
            .onSuccess { description ->
                _state.update {
                    it.copy(
                        selectedBook = it.selectedBook?.copy(description = description),
                        isLoading = false
                    )
                }
            }
            .onError {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
    }
}