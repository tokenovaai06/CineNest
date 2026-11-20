
package com.example.cinenest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingBadge(rating: Double) {
    Row(
        modifier = Modifier
            .background(
                color = Color(0xFF333333),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "★",
            color = Color(0xFFFFC107),
            fontSize = 16.sp
        )

        Text(
            text = " $rating",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}
