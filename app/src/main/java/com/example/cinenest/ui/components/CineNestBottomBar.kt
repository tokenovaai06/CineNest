package com.example.cinenest.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinenest.ui.navigation.Routes
import com.example.cinenest.ui.theme.CinemaRed
import com.example.cinenest.ui.theme.CinemaGray
import com.example.cinenest.ui.theme.CinemaSurface
import com.example.cinenest.ui.theme.CineNestTheme

enum class CineNestTab(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    HOME(Routes.MOVIE_LIST, "Home", Icons.Filled.Home),
    MATCH(Routes.MATCH, "Match", Icons.Filled.ThumbUp),
    MY_LIST(Routes.FAVORITES, "My list", Icons.Filled.Favorite)
}

@Composable
fun CineNestBottomBar(
    currentRoute: String?,
    onTabSelected: (CineNestTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = CinemaSurface
    ) {
        CineNestTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label
                    )
                },
                label = {
                    Text(text = tab.label)
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

@Preview(showBackground = true)
@Composable
fun CineNestBottomBarPreview() {
    CineNestTheme {
        CineNestBottomBar(
            currentRoute = Routes.MOVIE_LIST,
            onTabSelected = {}
        )
    }
}