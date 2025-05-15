package com.example.coffeapp.data.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Cart,
        BottomNavItem.Account,
    )

    NavigationBar(
        containerColor = Color(0xFFF5F5DC),
        contentColor = Color(0xFFB29982),
        modifier = Modifier.height(56.dp)

    ) {
        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry.value?.destination

        items.forEach { item ->
            val isSelected = currentDestination?.route == item.route
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.route, modifier = Modifier.scale(0.8f)
                ) },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6F4E37),
                    unselectedIconColor = Color(0xFFB29982),
                    indicatorColor = Color(0xFFFFE4C4)
                )
            )
        }
    }
}
