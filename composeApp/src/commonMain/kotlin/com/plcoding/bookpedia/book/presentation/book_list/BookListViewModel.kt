package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.book.domain.repository.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    private var cachedBooks = emptyList<Book>()

    private var searchJob: Job? = null
    private var favoriteBookJob: Job? = null

    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteBooks()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

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

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                isRefreshing = false,
                                isLoading = false,
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                isRefreshing = false,
            )
        }

        bookRepository.searchBooks(
            query = query
        )
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        searchResults = searchResults,
                        isLoading = false,
                        isRefreshing = false,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        errorMessage = error.toUiText().toString(),
                        isLoading = false,
                        isRefreshing = false,
                    )
                }
            }
    }

    private fun observeFavoriteBooks() {
        favoriteBookJob?.cancel()
        favoriteBookJob = bookRepository.getFavoriteBooks()
            .onEach { favoriteBooks ->
                _state.update {
                    it.copy(
                        favoriteBooks = favoriteBooks
                    )
                }
            }.launchIn(viewModelScope)
    }

}