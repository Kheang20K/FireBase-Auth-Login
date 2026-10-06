package com.kheang.firebaseauthenticationloginregister.ui.components


import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel
import kotlinx.coroutines.launch


@Composable
fun SliderImage(
    viewModel: ProductViewModel,
    productId: Int
) {
    val productState by viewModel.productState.collectAsState()
    LaunchedEffect(Unit) {
        if (productState == null) viewModel.loadProducts()
    }
    val product = viewModel.getProductId(productId) ?: return

    val pagerState = rememberPagerState(pageCount = { product.images.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) { page ->
            AsyncImage(
                model = product.images[page],
                contentDescription = "Product Image ${page + 1}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Previous
        IconButton(
            onClick = {
                scope.launch {
                    val prev = (pagerState.currentPage - 1 + product.images.size) % product.images.size
                    pagerState.animateScrollToPage(prev)
                }
            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous",
                tint = Color.Black
            )
        }

        // Next
        IconButton(
            onClick = {
                scope.launch {
                    val next = (pagerState.currentPage + 1) % product.images.size
                    pagerState.animateScrollToPage(next)
                }
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color.Black
            )
        }
    }
}




