package com.plcoding.bookpedia.book.presentation

import androidx.lifecycle.ViewModel
import com.plcoding.bookpedia.book.domain.model.Book
import kotlinx.coroutines.flow.MutableStateFlow

class SelectedBookSharedViewModel:ViewModel() {
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook get() = _selectedBook

    fun onSelectBook(book: Book?){
        _selectedBook.value = book
    }
}