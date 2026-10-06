package com.kheang.firebaseauthenticationloginregister.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kheang.firebaseauthenticationloginregister.R
import com.kheang.firebaseauthenticationloginregister.domain.models.Review
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@Composable
fun CustomerReview(
    viewModel: ProductViewModel = hiltViewModel(),
    productId: Int
){
    val product = viewModel.getProductId(productId)?: return
    val reviews = product.reviews
    var showSheet by remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    showSheet = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Customer Review",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "keyArrow",
                modifier = Modifier
                    .size(32.dp)
            )
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (reviews.isEmpty()){
            Text(
                text = "No reviews yet",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }else{
            reviews.forEach { review ->
                Spacer(modifier = Modifier.height(12.dp))
                CAndR(review = review)
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
    BottomSheet(
        show = showSheet,
        onDismiss = {showSheet = false}
    ) {
        Text(
            text = "Customer Reviews",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Divider(
            color = Color.Gray,
            thickness = 1.dp
        )
        reviews.forEach { review ->
            Spacer(modifier = Modifier.height(12.dp))
            CAndR(review = review)
            Divider(
                color = Color.Gray,
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


    }


}
@Composable
fun CAndR(
    review: Review,
    modifier: Modifier = Modifier

){

    Column (
        modifier
            .fillMaxWidth()
            .background(Color.White)
    ){
        Row (
            modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.profilecustomer),
                contentDescription ="Customer",
                modifier.size(32.dp)
            )
            Spacer(modifier.width(8.dp))
            Column (
            ){
                Text(
                    text = review.reviewerName,
                    fontSize = 16.sp
                )
                Text(
                    text = review.date,
                    fontSize = 11.sp
                )
            }
            Spacer(modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(review.rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )
                }
                if (review.rating < 5) {
                    repeat(5 - review.rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier.height(8.dp))
        Row (
            modifier
                .fillMaxWidth()
                .padding(start = 48.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = review.comment,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
){
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
            dragHandle = null
        ) {
            content() // 👈 dynamic content goes here
        }
    }


}



@Composable
@Preview
fun Customer(){
}