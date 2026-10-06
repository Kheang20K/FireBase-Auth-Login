package com.kheang.firebaseauthenticationloginregister.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@Composable
fun BrandAndDes(
    viewModel: ProductViewModel,
    productId: Int
){
    val product = viewModel.getProductId(productId)?: return

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp, top = 8.dp)
        ){
            Text(
                text = "Brand :",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = product.brand?:"Unknown",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(12.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp)

        ) {
            Text(
                text = "Description",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(6.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp)

        ){
            Text(
                text = product.description ?: "No description available",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Justify
            )
        }


    }
}

@Composable
@Preview
fun Brand(){
}