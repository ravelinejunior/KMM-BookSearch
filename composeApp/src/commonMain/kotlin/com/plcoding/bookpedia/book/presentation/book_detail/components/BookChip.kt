package com.plcoding.bookpedia.book.presentation.book_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.core.presentation.LightBlue

enum class ChipSize {
    SMALL,
    MEDIUM,
    LARGE
}

@Composable
fun BookChip(
    modifier: Modifier = Modifier,
    size: ChipSize = ChipSize.SMALL,
    chipContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .widthIn(
                min = when(size){
                    ChipSize.SMALL -> 50.dp
                    ChipSize.MEDIUM -> 80.dp
                    ChipSize.LARGE -> 100.dp
                }
            )
            .clip(RoundedCornerShape(24.dp))
            .background(LightBlue)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
        contentAlignment = Alignment.Center
    ){
        chipContent()
    }

}