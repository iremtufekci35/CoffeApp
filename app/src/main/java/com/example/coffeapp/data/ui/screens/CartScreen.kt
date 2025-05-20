package com.example.coffeapp.data.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.ui.components.BottomNavItem
import com.example.coffeapp.data.ui.viewmodels.CartViewModel

@Composable
fun CartScreen(userId:Int, navController: NavController) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        cartViewModel.fetchCartItems(userId)
    }

    val totalPrice = cartItems.sumOf { it.price * it.quantity }
    Log.d("CartScreen", "Cart Items: $cartItems")
    LaunchedEffect(totalPrice) {
        Log.d("CartScreen", "Toplam fiyat hesaplandı: ₺${"%.2f".format(totalPrice)}")
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Sepetim",
                style = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        if (cartItems.isEmpty()) {
            item {
                Text(
                    "Sepetiniz boş.",
                    style = TextStyle(fontSize = 18.sp, fontStyle = FontStyle.Italic),
                    color = Color.Gray
                )
            }
        } else {
            items(cartItems, key = { it.id }) { cartItem ->
                CartItemRow(cartItem = cartItem, cartViewModel = cartViewModel)
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Toplam:",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        )
                        Text(
                            text = "₺${"%.2f".format(totalPrice)}",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        cartViewModel.createOrder(
                            userId = userId,
                            items = cartItems,
                            onSuccess = {
                                    navController.navigate(BottomNavItem.Home.route)
                                    cartViewModel.clearCart(userId)

                            },
                            onError = { error ->
                                println("on error")
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Sipariş Ver",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, cartViewModel: CartViewModel) {
    Log.d("CartScreen", "Sepet öğeleri yüklendi: $cartItem öğe")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4C4))
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cartItem.itemName,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fiyat: ${cartItem.price}₺",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { cartViewModel.updateQuantity(cartItem, cartItem.quantity - 1) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = "Azalt")
                }
                Text(
                    text = "${cartItem.quantity}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                IconButton(
                    onClick = { cartViewModel.updateQuantity(cartItem, cartItem.quantity + 1) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Arttır")
                }
            }

            IconButton(
                onClick = {
//                    cartViewModel.removeItem(cartItem)
                          },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Ürünü Sil",
                    tint = Color.Red
                )
            }
        }
    }
}
