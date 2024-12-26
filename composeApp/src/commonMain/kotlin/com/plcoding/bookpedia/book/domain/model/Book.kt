package com.plcoding.bookpedia.book.domain.model

import io.ktor.http.HttpHeaders.Date
import kotlin.random.Random

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
) {
    companion object {
        fun generateMockBooks(): List<Book> {
            val authorsPool = listOf("Author A", "Author B", "Author C", "Author D", "Author E")
            val languagesPool = listOf("English", "Portuguese", "Spanish", "French", "German")
            val titlesPool = listOf(
                "Mystery of the Night", "Journey to the Stars", "The Lost City",
                "Echoes of the Past", "Beyond the Horizon", "Whispers in the Wind"
            )

            return List(100) { id ->
                Book(
                    id = id + 1,
                    title = titlesPool.random() + " (${Random.nextInt(1, 101)})",
                    imageUrl = "https://example.com/image_${id + 1}.jpg",
                    description = "Description of book ${id + 1}",
                    authors = List(Random.nextInt(1, 4)) { authorsPool.random() },
                    languages = List(Random.nextInt(1, 3)) { languagesPool.random() },
                    firstPublishYear = if (Random.nextBoolean()) (1900 + Random.nextInt(
                        0,
                        123
                    )).toString() else "2021-06-05",
                    averageRating = if (Random.nextBoolean()) Random.nextDouble(1.0, 5.0) else 3.0,
                    ratingCount = if (Random.nextBoolean()) Random.nextInt(1, 10000) else null,
                    numPages = if (Random.nextBoolean()) Random.nextInt(50, 1000) else null,
                    numEditions = Random.nextInt(1, 10)
                )
            }
        }
    }
}
