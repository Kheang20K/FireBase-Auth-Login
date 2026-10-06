//package com.kheang.firebaseauthenticationloginregister.ui.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import coil.compose.AsyncImage
//import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel
//
//@Composable
//fun CardDashboard(
//    productViewModel: ProductViewModel = hiltViewModel()
//){
//    val productResponse by productViewModel.productState.collectAsState()
//
//    val firstProduct = productResponse?.products?.firstOrNull()
//    Card (
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(180.dp)
//            .padding(8.dp),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(8.dp)
//    ){
//        if (firstProduct != null){
//            AsyncImage(
//                model = firstProduct.thumbnail,
//                contentDescription = firstProduct.title,
//                modifier = Modifier
//                    .fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//        }else{
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.LightGray),
//                verticalArrangement = Arrangement.Center
//            ) { }
//        }
//    }
//}
//
//
//@Preview
//@Composable
//fun pv(){
//    CardDashboard()
//}