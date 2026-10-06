package com.kheang.firebaseauthenticationloginregister.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kheang.firebaseauthenticationloginregister.R
import com.kheang.firebaseauthenticationloginregister.domain.models.Product
import com.kheang.firebaseauthenticationloginregister.domain.remote.ApiResult
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@Composable
fun AllCategory(
    productViewModel: ProductViewModel = hiltViewModel(),
    navController: NavController,
) {
    val productResponse by productViewModel.productState.collectAsState()

    val categories = listOf("All", "drinks", "fruit", "cosmetic", "interior")
    val selectedCategory = remember { mutableStateOf("All") }

    val products = when (productResponse) {
        is ApiResult.Success<*> -> {
            val allProducts = (productResponse as ApiResult.Success<Product>).data.products
            if (selectedCategory.value == "All") allProducts
            else productViewModel.filterByCategory(selectedCategory.value)

        }
        else -> emptyList()
    }
    val isLoading = productResponse is ApiResult.Loading
    val isError = productResponse is ApiResult.Error
    val errorMessage = if (productResponse is ApiResult.Error)(productResponse as ApiResult.Error).message else ""

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            )
    ) {
        // 🔹 Category Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                AssistChip(
                    onClick = { selectedCategory.value = category },
                    label = {
                        Text(
                            text = category.replaceFirstChar { it.uppercase() },
                            color = if (selectedCategory.value == category) Color.White
                            else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (selectedCategory.value == category) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading products...")
                }
            }
            isError ->{
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("⚠️ $errorMessage")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { productViewModel.loadProducts() }) {
                        Text("Retry")
                    }
                }

            }
            products.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No products available")
                }
            }
            else -> {
                // Products in rows of 2
                products.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding( top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        row.forEach { product ->
                            val discountPrice = product.price * (1 - product.discountPercentage /100)
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(320.dp)
                                    .clickable {
                                        navController.navigate("productDetail/${product.id}") // correct
                                    },
                                elevation = CardDefaults.cardElevation(4.dp),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Column {
                                    Card (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp),
                                        elevation = CardDefaults.cardElevation(1.dp),
                                        shape = RoundedCornerShape(6.dp)

                                    ){
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Row (
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp),
                                                horizontalArrangement = Arrangement.End
                                            ){
                                                Icon(
                                                    imageVector = Icons.Default.FavoriteBorder,
                                                    contentDescription = "favorite",
                                                    modifier = Modifier
                                                        .size(28.dp)
                                                )
                                            }
                                            AsyncImage(
                                                model = product.thumbnail,
                                                contentDescription = product.title,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(160.dp)
                                                    .padding(5.dp)
                                                    .clip(RoundedCornerShape(6.dp)),
                                                contentScale = ContentScale.Crop
                                            )

                                        }
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(6.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = product.title,
                                                fontWeight = FontWeight.SemiBold,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        Text(
                                            text = "brand: ${product.brand}",
                                            maxLines = 1,
                                            fontSize = 16.sp,
                                            color = Color.Gray

                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ){
                                            Text(
                                                text = "$${"%.2f".format(product.price)}",
                                                fontSize = 13.sp,
                                                maxLines = 1,
                                                fontWeight = FontWeight.Normal,
                                                textDecoration = TextDecoration.LineThrough,
                                                color = Color.Gray
                                            )
                                            Spacer(Modifier.width(6.dp))
                                            Text(
                                                text = "Saving -${"%.2f".format(product.discountPercentage)}%",
                                                fontSize = 12.sp,
                                                maxLines = 1,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.Red
                                            )

                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "$${"%.2f".format(discountPrice)}",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF2E7D32) // Green color
                                            )
                                            Spacer(Modifier.width(20.dp))
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = null,
                                                    tint = Color(0xFFFFD700),
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Spacer(Modifier.width(4.dp))
                                                Text(
                                                    text = "${product.rating}",
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun Card(){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(6.dp)

    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ){
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "favorite",
                    modifier = Modifier
                        .size(35.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "image",
                modifier = Modifier
                    .height(180.dp)
            )

        }
    }
}

