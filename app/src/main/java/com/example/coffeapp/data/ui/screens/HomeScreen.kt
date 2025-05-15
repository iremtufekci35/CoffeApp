package com.example.coffeapp.data.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
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
import com.example.coffeapp.data.ui.viewmodels.CartViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coffeapp.data.local.datastore.DataStoreManager
import com.example.coffeapp.data.ui.viewmodels.FavoriteViewModel
import com.example.coffeapp.data.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    dataStoreManager: DataStoreManager) {
    val isLoggedIn by homeViewModel.isLoggedIn.collectAsState(initial = false)
//    val userId by homeViewModel.userId.collectAsState(initial = 0)
    val userId by dataStoreManager.userId.collectAsState(initial = 0)

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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoggedIn) {
            Text("Hoş geldiniz! Kullanıcı ID: $userId")
        } else {
            Text("Lütfen giriş yapın.")
        }

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
        CoffeeGrid(coffees = coffeeList, onAddToCart = { cartItem -> cartViewModel.insertCartItem(cartItem,userId) })
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

    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()

    val isLoggedIn by homeViewModel.isLoggedIn.collectAsState(initial = false)
    val userId by homeViewModel.userId.collectAsState(initial = 0)

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${item.price}₺",
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val cartItem = CartItem(
                            itemName = item.name,
                            price = item.price.toDouble(),
                            imageRes = item.imageRes,
                            quantity = 1,
                            userId = userId
                        )
                        onAddToCart(cartItem)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Sepete Ekle",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sepete Ekle", fontSize = 12.sp)
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 6.dp, top = 6.dp)
                    .size(28.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
            ) {
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                        if (isFavorite && isLoggedIn) {
                            favoriteViewModel.addItemToFav(userId, item.name)
                        }
                    },
                    modifier = Modifier
                        .size(24.dp)
                        .padding(0.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
