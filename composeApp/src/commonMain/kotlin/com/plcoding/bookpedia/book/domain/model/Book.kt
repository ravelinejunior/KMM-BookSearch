package com.plcoding.bookpedia.book.domain.model

data class Book(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val description: String,
    val authors: List<String>,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int
)
