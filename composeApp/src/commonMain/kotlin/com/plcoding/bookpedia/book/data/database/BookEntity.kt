package com.plcoding.bookpedia.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books_table")
data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id:String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val languages: List<String>?,
    val authors: List<String>?,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPagesMedian: Int?,
    val numEditions: Int

)
