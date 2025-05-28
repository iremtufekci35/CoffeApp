package com.example.coffeapp.data.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Beige = Color(0xFFF5F5DC)
val LightBrown = Color(0xFFB29982)
val DarkBrown = Color(0xFF6F4E37)
val LightBeige = Color(0xFFFFE4C4)

private val LightColorScheme = lightColorScheme(
    primary = DarkBrown,
    onPrimary = Color.White,
    secondary = LightBrown,
    background = Beige,
    surface = LightBeige,
)

@Composable
fun CoffeeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(),
        content = content
    )
}

