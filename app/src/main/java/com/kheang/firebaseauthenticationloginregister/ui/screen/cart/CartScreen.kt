package com.kheang.firebaseauthenticationloginregister.ui.screen.cart

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kheang.firebaseauthenticationloginregister.domain.database.CartItem
import com.kheang.firebaseauthenticationloginregister.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel(),
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Cart",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable{
                                navController.popBackStack()
                            }
                            .size(33.dp)
                    )
                },
                modifier = Modifier
                    .shadow(elevation = 2.dp),
            )
        }
    ) { innerPadding->
        val cartItems by viewModel.cartItems.collectAsState()
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cartItems) { item ->
                LayoutCart(
                    asyncImage = item.image,
                    title = item.title,
                    afterPrice = "$${"%.2f".format(item.price)}",
                    qty = "Qty: x${item.quantity}",
                    onDeleteConfirmed = {
                        viewModel.removeFromCart(item)
                    }
                )
            }
        }
    }
}
@SuppressLint("RememberReturnType")
@Composable
fun LayoutCart(
    asyncImage: String,
    title: String,
    afterPrice: String,
    qty: String,
    onDeleteConfirmed: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .shadow(2.dp, RoundedCornerShape(4.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .height(110.dp)
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = asyncImage,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column (
                modifier = Modifier
                    .width(190.dp)
                    .padding(bottom = 5.dp),
            ){
                Text(
                    text = title,
                    fontSize = 16.sp,
                    maxLines = 1,
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = afterPrice,
                    fontSize = 18.sp,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = qty,
                    fontSize = 16.sp,
                    color = Color.Gray,
                )
            }
            Spacer(Modifier.weight(1f))
            Column (
                modifier = Modifier
                    .padding( end = 8.dp),
                ){
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable{
                            showDialog = true
                        }
                )
                if (showDialog){
                    AlertDialog(
                        onDismissRequest = { showDialog =false},
                        title = {
                            Text(
                                text = "Confirm Delete"
                            )
                        },
                        text = { Text("Are you sure you want to delete this item?") },
                        confirmButton = {
                            Text(
                                text = "Yes",
                                color = Color.Red,
                                modifier = Modifier
                                    .clickable {
                                        onDeleteConfirmed()
                                        showDialog = false
                                    }
                                    .padding(8.dp)
                            )
                        },
                        dismissButton = {
                            Text(
                                text = "Cancel",
                                color = Color.Gray,
                                modifier = Modifier
                                    .clickable { showDialog = false }
                                    .padding(8.dp)
                            )
                        }
                    )
                }
            }

        }
    }
}