package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.domain.model.Book
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    bookListViewModel: BookListViewModel = koinViewModel<BookListViewModel>(),
    onBookClick: (Book) -> Unit,
) {
    val state by bookListViewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state,
        action = { action ->
            when (action) {
                is BookListAction.OnBookClickAction -> onBookClick(action.book)
                else -> Unit
            }
            bookListViewModel.onAction(action)
        }
    )

}

@Composable
fun BookListScreen(
    state: BookListState,
    action: (BookListAction) -> Unit
) {

}