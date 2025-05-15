package com.example.coffeapp.data.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, onOrdersClick: () -> Unit, onLogoutClick: () -> Unit) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Çıkış Yap") },
            text = { Text(" Çıkış yapmak istiyor musunuz?") },
            icon = {Icons.AutoMirrored.Filled.ExitToApp},
            confirmButton = {
                Text(
                    "Evet",
                    modifier = Modifier
                        .clickable {
                            showLogoutDialog = false
                            onLogoutClick()
                        }
                        .padding(8.dp)
                )
            },
            dismissButton = {
                Text(
                    "Hayır",
                    modifier = Modifier
                        .clickable { showLogoutDialog = false }
                        .padding(8.dp)
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Hesabım", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(24.dp))

        ProfileMenuItem(title = "🛒 Siparişlerim", onClick = onOrdersClick)
        ProfileMenuItem(title = "🚪 Çıkış Yap", onClick = { showLogoutDialog = true })
    }
}


@Composable
fun ProfileMenuItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
            .background(Color(0xFFB29982))
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .clip(MaterialTheme.shapes.medium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
