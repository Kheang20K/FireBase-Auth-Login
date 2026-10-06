package com.kheang.firebaseauthenticationloginregister.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kheang.firebaseauthenticationloginregister.R
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@Composable
fun AllShipping(
    viewModel: ProductViewModel,
    productId : Int
){
    val product = viewModel.getProductId(productId)?: return
    Column (
        modifier = Modifier
            .fillMaxWidth()
//            .height(140.dp)
            .background(Color.White)
    ){
        RowItem(
            iconRes = R.drawable.shipping,
            value = product.shippingInformation
        )
        RowItem(
            iconRes = R.drawable.img_warranty,
            value = product.warrantyInformation
        )
        RowItem(
            iconRes = R.drawable.img_policy,
            value = product.returnPolicy
        )
        RowItem(
            iconRes = R.drawable.img_box,
            value = "Dimensions: ${product.dimensions.width} x ${product.dimensions.height} x ${product.dimensions.depth} cm"
        )
    }
}

@Composable
fun RowItem(
    iconRes: Int,
    value: String,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "",
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column {

            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }


}