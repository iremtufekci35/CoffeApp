package com.example.coffeapp.data.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.ui.viewmodels.CartViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coffeapp.data.ui.components.BottomNavItem

@Composable
fun OrderScreen(navController: NavController) {
    val cartViewModel: CartViewModel = hiltViewModel()

    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        cartViewModel.getAllItems()
        cartItems = cartViewModel.cartItems.value ?: emptyList()
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {

        Text(
            text = "Siparişlerim",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartItems.isNotEmpty()) {
            cartItems.forEach { cartItem ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = cartItem.productName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = "Price: \$${cartItem.price}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Qty: ${cartItem.quantity}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your cart is empty.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = {
                        navController.navigate(BottomNavItem.Home.route) {
                            popUpTo("order_screen_route") {
                                inclusive = true
                            }
                        }
                        },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Start Shopping")
                }
            }
        }
    }
}
