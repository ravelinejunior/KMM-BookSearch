package com.plcoding.bookpedia.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_cover
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import cmp_bookpedia.composeapp.generated.resources.favorites_saved
import cmp_bookpedia.composeapp.generated.resources.go_back
import cmp_bookpedia.composeapp.generated.resources.no_favorite_books
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val mPainter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Throwable("Image size is too small"))
            }
        },
        onError = {
            it.result.throwable.printStackTrace()
        }
    )

    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
                    .background(DarkBlue)
            ) {
                if (imageLoadResult?.isSuccess == true) {
                    Image(
                        painter = mPainter,
                        contentDescription = stringResource(Res.string.book_cover),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Transparent.copy(alpha = 0.5f))
                            .blur(20.dp)

                    )
                } else {
                    CircularProgressIndicator()
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .background(DesertWhite)
            )
        }
        Box(
            modifier = Modifier.padding(
                top = 8.dp,
                start = 8.dp
            )
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .height(40.dp)
                    .widthIn(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(40.dp)
                    )
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                    contentDescription = stringResource(Res.string.go_back),
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                ElevatedCard(
                    modifier = Modifier
                        .width(200.dp)
                        .aspectRatio(2 / 3f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.Transparent
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 16.dp
                    )
                ) {
                    AnimatedContent(
                        targetState = imageLoadResult,
                    ) { result ->
                        when (result) {
                            null -> CircularProgressIndicator()
                            else -> {
                                Box {
                                    Image(
                                        painter = if (result.isSuccess) mPainter else painterResource(
                                            Res.drawable.book_error_2
                                        ),
                                        contentDescription = stringResource(Res.string.book_cover),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = Color.Transparent),
                                        contentScale = if (result.isSuccess) {
                                            ContentScale.Crop
                                        } else {
                                            ContentScale.Fit
                                        }
                                    )
                                    IconButton(
                                        onClick = onFavoriteClick,
                                        modifier = Modifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .background(
                                                brush = Brush.radialGradient(
                                                    colors = listOf(
                                                        MaterialTheme.colorScheme.onSurface.copy(
                                                            alpha = 0.8f
                                                        ),
                                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                                    ),
                                                    radius = 100f
                                                ),
                                                shape = RoundedCornerShape(40.dp)
                                            )
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isFavorite) {
                                                Icons.Filled.Star
                                            } else {
                                                Icons.Outlined.Star
                                            },
                                            contentDescription = if (isFavorite) {
                                                stringResource(Res.string.favorites_saved)
                                            } else {
                                                stringResource(Res.string.no_favorite_books)
                                            },
                                            tint = Color.Yellow,
                                        )
                                    }

                                }
                            }

                        }

                    }
                }
                content()
            }
        }
    }

}