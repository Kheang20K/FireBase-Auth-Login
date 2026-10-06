package com.kheang.firebaseauthenticationloginregister.ui.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kheang.firebaseauthenticationloginregister.domain.database.CartItem
import com.kheang.firebaseauthenticationloginregister.viewmodel.CartViewModel
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@Composable
fun CustomSheet(
    viewModel: ProductViewModel = hiltViewModel(),
    productId:Int,
    type: String,
    navController: NavController,
    viewModelCart: CartViewModel = hiltViewModel(),

){
    val product = viewModel.getProductId(productId) ?: return
    var quantity by remember(productId) { mutableStateOf(1) }

    val context = LocalContext.current


    val originalPricePerItem = product.price
    val discountAmount = product.price * (1 - product.discountPercentage /100)
    val discountPrice = originalPricePerItem - discountAmount

    val originalTotal = originalPricePerItem * quantity
    val discountTotal = discountPrice * quantity
    val totalPrice = discountAmount * quantity


    val titleTotal= listOf("Original","Discount","Total")
    val currentTotal = listOf(
        "$${"%.2f".format(originalPricePerItem)}",
        "-$${"%.2f".format(discountTotal)}",
        "$${"%.2f".format(totalPrice)}"
    )
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.title,
                fontSize = 18.sp
            )
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp
        )
        Spacer(Modifier.height(16.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ){
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .height(130.dp)
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = product.thumbnail,
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            ){
                Row {
                    Text(
                        text = "$${"%.2f".format(discountAmount)}",
                        fontSize = 16.sp,
                        maxLines = 1,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = "|"
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "$${"%.2f".format(product.price)}",
                        fontSize = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Normal,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )
                }
                Spacer(Modifier.height(12.dp))
                Row (
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = {
                            if (quantity > 1) quantity--
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color.Gray
                        )
                    ) {
                        Text(
                            text = "-",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(Modifier.width(12.dp))

                    Text(
                        text = "$quantity",
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.width(12.dp))

                    Button(
                        onClick = {
                            quantity++
                        },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color.Gray
                        )
                    ) {
                        Text(
                            text = "+",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Iterate through both lists at the same time
            titleTotal.forEachIndexed { index, label ->
                val value = currentTotal[index] // Get the corresponding value

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1. Label on the left
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge, // Use a clear style
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // 2. Value on the right
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold, // Make the value stand out
                        color = when (index) {
                            1 -> Color.Red // Example: Tax
                            2 -> Color(0xFF4D9D4F) // Example: Total
                            else -> Color.Black // Example: Subtotal
                        }
                    )
                }
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {
            if (type == "add"){
                GradientButton(
                    text ="Add to Cart",
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFC107),Color(0xFFFFA000))
                    ),
                    corners = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp)
                        .height(40.dp),
                    onClick = {
                        viewModelCart.addToCart(
                            CartItem(
                                id = product.id,
                                title = product.title,
                                price = totalPrice,
                                image = product.thumbnail,
                                quantity = quantity
                            )
                        )
                        Toast.makeText(context,"Your Item is add to cart", Toast.LENGTH_SHORT).show()
//                        navController.navigate("cart")
                    }
                )
            }else if (type == "buy"){
                GradientButton(
                    text = "Buy now" ,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFC107),Color(0xFFFFA000))
                    ),
                    corners = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp)
                        .height(40.dp),
                    onClick = {

                    }
                )
            }
        }
    }
}
