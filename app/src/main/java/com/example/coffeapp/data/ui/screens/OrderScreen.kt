package com.example.coffeapp.data.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coffeapp.data.ui.viewmodels.CartViewModel
import com.example.coffeapp.data.ui.components.BottomNavItem

@Composable
fun OrderScreen(navController: NavController, userId: Int) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val orders by cartViewModel.orders.observeAsState()

    LaunchedEffect(Unit) {
        cartViewModel.fetchOrders(userId)
    }

    fun formatDate(dateString: String): String {
        return try {
            val parsedDate = java.time.ZonedDateTime.parse(dateString)
            val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
            parsedDate.format(formatter)
        } catch (e: Exception) {
            dateString
        }
    }

    if (orders.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Siparişiniz Yok.",
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
                Text(text = "Alışverişe Başla")
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            items(orders!!) { order ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Sipariş Tarihi: ${formatDate(order.createdAt)}")
                        Text("Toplam Fiyat: ₺${String.format("%.2f", order.totalPrice)}")
                        Spacer(modifier = Modifier.height(8.dp))
//                        order.orderItems.forEach { item ->
//                            val itemTotal = item.quantity * item.price
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Text(text = item.itemName)
//                                Text(
//                                    text = "${item.quantity} x \$${item.price} = \$${
//                                        String.format(
//                                            "%.2f",
//                                            itemTotal
//                                        )
//                                    }"
//                                )
//                            }
//                        }
                    }
                }
            }
        }
    }
}
