package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.empty_list
import cmp_bookpedia.composeapp.generated.resources.favorites_saved
import cmp_bookpedia.composeapp.generated.resources.no_favorite_books
import cmp_bookpedia.composeapp.generated.resources.search_results
import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookList
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    bookListViewModel: BookListViewModel = koinViewModel<BookListViewModel>(),
    onBookClick: (Book) -> Unit,
) {
    val state by bookListViewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state,
        onAction = { action ->
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
    onAction: (BookListAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val lazyListStateSearchResult = rememberLazyListState()
    val lazyListStateFavorites = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        lazyListStateSearchResult.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(
            BookListAction.OnTabSelectedAction(pagerState.currentPage)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp),
            searchQuery = state.searchQuery,
            onSearchQueryChanged = { query ->
                onAction(BookListAction.OnSearchQueryAction(query = query))
            },
            keyboardController = keyboardController,
            onImeSearch = {
                keyboardController?.hide()
            }
        )

        // BookList
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 16.dp),
            color = DesertWhite,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    contentColor = DarkBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = DarkBlue,
                            modifier = Modifier.tabIndicatorOffset(
                                tabPositions[state.selectedTabIndex]
                            )
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.OnTabSelectedAction(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = DarkBlue,
                        unselectedContentColor = DarkBlue.copy(alpha = 0.5f)
                    ) {
                        Text(
                            stringResource(Res.string.search_results),
                            style =
                            MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.OnTabSelectedAction(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = DarkBlue,
                        unselectedContentColor = DarkBlue.copy(alpha = 0.5f)
                    ) {
                        Text(
                            stringResource(Res.string.favorites_saved),
                            style =
                            MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator(
                                        color = SandYellow,
                                    )
                                } else {
                                    when {
                                        (state.errorMessage != null) ->
                                            Text(
                                                text = state.errorMessage,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = DarkBlue
                                            )

                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.empty_list),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = DarkBlue
                                            )
                                        }

                                        else -> {
                                            BookList(
                                                books = state.searchResults,
                                                onBookClick = { book ->
                                                    onAction(BookListAction.OnBookClickAction(book))
                                                },
                                                scrollState = lazyListStateSearchResult
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator(
                                        color = SandYellow,
                                    )
                                } else {
                                    when {
                                        (state.errorMessage != null) ->
                                            Text(
                                                text = state.errorMessage,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = DarkBlue
                                            )

                                        state.favoriteBooks.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_favorite_books),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = DarkBlue
                                            )
                                        }

                                        else -> {
                                            BookList(
                                                books = state.favoriteBooks,
                                                onBookClick = { book ->
                                                    onAction(BookListAction.OnBookClickAction(book))
                                                },
                                                scrollState = lazyListStateFavorites
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}