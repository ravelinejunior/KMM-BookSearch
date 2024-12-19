package com.plcoding.bookpedia

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookListItem
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun BookSearchBarPreview() {
    MaterialTheme {
        BookSearchBar(
            searchQuery = "Book",
            onImeSearch = {},
            onSearchQueryChanged = {},
            keyboardController = LocalSoftwareKeyboardController.current
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
fun BookListItemPreview() {
    MaterialTheme {
        BookListItem(
            book = Book(
                id = 1,
                title = "Book Title",
                authors = listOf("Author 1", "Author 2"),
                imageUrl = "https://images.unsplash.com/photo-1622838320000-4b3b3b3b3b3b",
                averageRating = 4.3,
                ratingCount = 100,
                numPages = 200,
                languages = listOf("English"),
                description = "Book Description",
                numEditions = 1,
                firstPublishYear = "2021-06-05",
            ),
            onBookClick = {}
        )
    }

}