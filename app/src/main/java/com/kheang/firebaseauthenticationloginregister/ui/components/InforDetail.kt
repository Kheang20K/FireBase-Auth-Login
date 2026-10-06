package com.kheang.firebaseauthenticationloginregister.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@Composable
fun InforDetail(
    viewModel: ProductViewModel,
    productId: Int
){
    val product = viewModel.getProductId(productId) ?: return

    val discountPrice = product.price * (1 - product.discountPercentage /100)
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.White)
            .padding(8.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding( top = 12.dp)
        ){
            Text(
                text = product.title,
                fontSize = 22.sp,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(18.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "$${"%.2f".format(product.price)}",
                fontSize = 16.sp,
                maxLines = 1,
                fontWeight = FontWeight.Normal,
                textDecoration = TextDecoration.LineThrough,
                color = Color.Gray
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = "$${"%.2f".format(discountPrice)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32) // Green color
            )
        }
        Spacer(Modifier.height(4.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(Modifier.width(6.dp))
            Text(
                text = "Saving -${"%.2f".format(product.discountPercentage)}%",
                fontSize = 12.sp,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                color = Color.Red
            )
        }


    }

}
