package com.example.coffeapp.data.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.CoffeeItem
import com.example.coffeapp.data.ui.cart.CartViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen() {
    val tabs = listOf("Sıcak", "Soğuk")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val cartViewModel: CartViewModel = hiltViewModel()

    val hotCoffees = listOf(
        CoffeeItem("Latte", "40", "https://images.unsplash.com/photo-1511920170033-f8396924c348"),
        CoffeeItem("Espresso", "30", "https://images.unsplash.com/photo-1587732440609-1965a3a5c7b2"),
        CoffeeItem("Cappuccino", "35", "https://images.unsplash.com/photo-1527168020762-3f3b5d2f3c91")
    )

    val coldCoffees = listOf(
        CoffeeItem("Mocha", "42", "https://images.unsplash.com/photo-1524592321522-6a9f7e640420"),
        CoffeeItem("Americano", "33", "https://images.unsplash.com/photo-1605478201968-38f3be0349a1")
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val coffeeList = if (selectedTabIndex == 0) hotCoffees else coldCoffees
        CoffeeGrid(coffees = coffeeList, onAddToCart = { cartItem -> cartViewModel.insertCardItem(cartItem) })
    }
}

@Composable
fun CoffeeGrid(coffees: List<CoffeeItem>, onAddToCart: (CartItem) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(coffees.size) { index ->
            val item = coffees[index]
            CoffeeCard(item = item, onAddToCart = onAddToCart)
        }
    }
}
@Composable
fun CoffeeCard(item: CoffeeItem, onAddToCart: (CartItem) -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageRes)
                        .crossfade(true)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )

                // Add Spacer to separate name and price from the image
                Spacer(modifier = Modifier.height(8.dp))

                // Product name
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                // Product price
                Text(
                    text = "${item.price}₺",
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp)) // Add some space before the button
            }

            // Favorite Icon button
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
            ) {
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Add to cart button
            Button(
                onClick = {
                    val cartItem = CartItem(
                        productName = item.name,
                        price = item.price.toDouble(),
                        imageRes = item.imageRes,
                        quantity = 1
                    )
                    onAddToCart(cartItem)
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Sepete Ekle",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sepete Ekle",
                    fontSize = 12.sp,
                )
            }
        }
    }
}



