package com.kheang.firebaseauthenticationloginregister.ui.screen.detail


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kheang.firebaseauthenticationloginregister.ui.components.AllShipping
import com.kheang.firebaseauthenticationloginregister.ui.components.BottomSheet
import com.kheang.firebaseauthenticationloginregister.ui.components.BrandAndDes
import com.kheang.firebaseauthenticationloginregister.ui.components.ButtonBar
import com.kheang.firebaseauthenticationloginregister.ui.components.CustomSheet
import com.kheang.firebaseauthenticationloginregister.ui.components.CustomerReview
import com.kheang.firebaseauthenticationloginregister.ui.components.InforDetail
import com.kheang.firebaseauthenticationloginregister.ui.components.SliderImage
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailProductScreen(
    productId: Int,
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
){
    val productState = viewModel.productState.collectAsState().value
    var showSheet by remember { mutableStateOf(false) }
    var sheetType by remember { mutableStateOf("") }

    if (productState == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        // Trigger loading if not already
        LaunchedEffect(Unit) {
            viewModel.loadProducts()
        }
        return
    }
    val product = viewModel.getProductId(productId) ?: run {
        Text("Product not found")
        return
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Product Detail",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable{
                                navController.popBackStack()
                            }
                    )
                },
                modifier = Modifier
                    .shadow(elevation = 2.dp),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(80.dp),
                backgroundColor = Color.White,
                elevation = 4.dp,
            ) {
                ButtonBar(
                    onAddToCartClick = {
                        sheetType = "add"
                        showSheet = true
                    },
                    onBuyNOwClick = {
                        sheetType = "buy"
                        showSheet = true
                    },
                    navController = navController,
                    viewModel = viewModel,
                    productId = productId
                )
            }
        }
    ){ innerPadding->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Spacer(Modifier.height(6.dp))
                SliderImage(viewModel = viewModel, productId = productId)
            }
            item {
                Spacer(Modifier.height(6.dp))

                InforDetail(viewModel = viewModel, productId = productId)
            }
            item {
                Spacer(Modifier.height(6.dp))

                AllShipping(viewModel = viewModel, productId = productId)
            }
            item {
                Spacer(Modifier.height(6.dp))

                BrandAndDes(viewModel = viewModel, productId = productId)
            }
            item {
                Spacer(Modifier.height(6.dp))

                CustomerReview(viewModel = viewModel, productId = productId)
            }
        }
        if (showSheet){
            BottomSheet(
                show = showSheet,
                onDismiss = {showSheet = false}
            ) {
                CustomSheet(viewModel = viewModel, productId = productId, type =sheetType,navController)
            }
        }
    }

}