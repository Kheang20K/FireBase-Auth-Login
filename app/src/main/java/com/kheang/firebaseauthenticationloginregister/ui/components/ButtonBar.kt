package com.kheang.firebaseauthenticationloginregister.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kheang.firebaseauthenticationloginregister.R
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@SuppressLint("InvalidColorHexValue")
@Composable
fun ButtonBar(
    onAddToCartClick: () -> Unit,
    onBuyNOwClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    productId: Int,
    viewModel: ProductViewModel = hiltViewModel()
){
//    var showSheet by remember(productId) { mutableStateOf(false) }
//    var sheetType by remember { mutableStateOf("") }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Column (
            modifier = Modifier
        ){
            Image(
                painter = painterResource(id = R.drawable.img_store),
                contentDescription = "store"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row (
            modifier = modifier
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically

        ){
             GradientButton(
                 text = "Add Cart",
                 gradient = Brush.horizontalGradient(
                     colors = listOf(Color(0xFFFFC107),Color(0xFFFFA000))
                 ),
                 corners = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp),
                 modifier = modifier
                     .width(120.dp)
                     .height(40.dp),
                 onClick = onAddToCartClick
             )
            Spacer(modifier.width(2.dp))
            GradientButton(
                text = "Buy Now",
                gradient = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFF9800),Color(0xFFFF57C00))
                ),
                corners = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
                modifier = modifier
                    .width(120.dp)
                    .height(40.dp),
                onClick = onBuyNOwClick
            )
        }
    }

}

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    corners: RoundedCornerShape,
    modifier: Modifier= Modifier,
    onClick: () -> Unit

){
    Box(
        modifier = modifier
            .background(brush = gradient, shape = corners)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }

}

@Composable
@Preview
fun BottomPreview(){

}