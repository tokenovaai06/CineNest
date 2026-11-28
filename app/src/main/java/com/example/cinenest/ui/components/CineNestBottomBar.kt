package com.example.cinenest.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.cinenest.ui.navigation.Routes
import com.example.cinenest.ui.theme.CinemaRed
import com.example.cinenest.ui.theme.CinemaGray
import com.example.cinenest.ui.theme.CinemaSurface

@Composable
fun BottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        Triple("Home", Routes.MOVIE_LIST, Icons.Filled.Home),
        Triple("Match", Routes.MATCH, Icons.Filled.Favorite),
        Triple("My list", Routes.FAVORITES, Icons.Filled.List)
    )

    NavigationBar(
        containerColor = CinemaSurface
    ) {
        items.forEach { (label, route, icon) ->
            NavigationBarItem(
                selected = currentRoute == route,
                onClick = { onNavigate(route) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(text = label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CinemaRed,
                    selectedTextColor = CinemaRed,
                    unselectedIconColor = CinemaGray,
                    unselectedTextColor = CinemaGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}