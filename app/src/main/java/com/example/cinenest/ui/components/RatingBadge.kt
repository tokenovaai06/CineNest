package com.example.cinenest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinenest.ui.theme.CinemaGold
import com.example.cinenest.ui.theme.CinemaWhite
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBadge(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "★",
            color = CinemaGold,
            fontSize = 16.sp
        )

        Text(
            text = " $rating",
            color = CinemaGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}