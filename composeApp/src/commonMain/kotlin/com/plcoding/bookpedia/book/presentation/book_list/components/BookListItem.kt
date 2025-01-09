package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.book.domain.model.Book
import com.plcoding.bookpedia.core.presentation.LightBlue
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun BookListItem(
    modifier: Modifier = Modifier,
    book: Book,
    onBookClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable(onClick = onBookClick),
        color = LightBlue.copy(alpha = 0.2f)
    ) {
        println("Image url: ${book.imageUrl}")
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image
            Box(
                modifier = Modifier
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl,
                    onSuccess = {
                        imageLoadResult =
                            if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                Result.success(it.painter)
                            } else {
                                Result.failure(Exception("Invalid image"))
                            }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        println(it.result.image)
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )

                when (val result = imageLoadResult) {
                    null -> CircularProgressIndicator(
                        modifier = Modifier.size(50.dp).align(Alignment.Center)
                    )

                    else -> {
                        Image(
                            painter = if (result.isSuccess) painter else {
                                painterResource(Res.drawable.book_error_2)
                            },
                            contentDescription = book.title,
                            contentScale = if (result.isSuccess) ContentScale.Crop
                            else ContentScale.Fit,
                            modifier = Modifier
                                .aspectRatio(
                                    ratio = 0.65f,
                                    matchHeightConstraintsFirst = true
                                )
                        )
                    }
                }
            }
            // Book details
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book.authors.joinToString(" "),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                book.averageRating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val formattedRating = round(rating * 10) / 10.0
                        val intRating = formattedRating.toInt()
                        Text(
                            text = "Rating: $formattedRating",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        for (i in 1..5) {
                            val icon =
                                if (
                                    i <= intRating ||
                                    (i == intRating + 1 && formattedRating - intRating >= 0.5)
                                ) {
                                    Icons.Filled.Star
                                } else {
                                    Icons.TwoTone.Star
                                }
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = SandYellow,
                            )
                        }
                    }
                }
                Text(
                    text = book.firstPublishYear.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            //Arrow go
            Image(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Arrow go",
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}