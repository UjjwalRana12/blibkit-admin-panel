import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.android.blinkitadminjc.R
import com.android.blinkitadminjc.model.HomeItem
import com.android.blinkitadminjc.model.Product
import com.android.blinkitadminjc.viewmodel.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@Composable
fun homeScreen(navController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()

    val products by homeViewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchProducts()
    }

    val blinkitFoodItems = listOf(
        HomeItem(R.drawable.homi1, "Banana - Fresh and ripe bananas, packed with energy."),
        HomeItem(R.drawable.homi2, "Apple - Crisp and juicy apples, full of flavor."),
        HomeItem(R.drawable.homi3, "Bread - Soft and fresh bread, perfect for sandwiches."),
        HomeItem(R.drawable.dawat, "Milk - Pure and nutritious milk for your daily needs."),
        HomeItem(R.drawable.dawat1, "Eggs - High-quality eggs, great for a protein-rich diet."),
        HomeItem(R.drawable.corneto1, "Tomato - Ripe and red tomatoes, essential for cooking."),
        HomeItem(R.drawable.attta1, "Potato - Versatile and tasty potatoes, great for any meal."),
        HomeItem(
            R.drawable.attta2,
            "Chicken - Fresh and tender chicken, ideal for various recipes."
        ),
        HomeItem(R.drawable.amulic1, "Onion - Fresh onions, a staple in many dishes."),
        HomeItem(
            R.drawable.amulic1,
            "Cucumber - Cool and refreshing cucumbers, perfect for salads."
        )
    )

    val searchText by homeViewModel.searchText.collectAsState()
    val isSearching by homeViewModel.isSearching.collectAsState()



    Column {
        SearchBar(homeViewModel = homeViewModel)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp)
        )



        FixRow(items = blinkitFoodItems)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        ImageTextGrid(products, 2)

    }
}


@Composable
fun SearchBar(homeViewModel: HomeViewModel) {
    val searchText by homeViewModel.searchText.collectAsState()

    OutlinedTextField(
        value = searchText,
        onValueChange = { homeViewModel.updateSearchText(it) },
        placeholder = { Text(text = "Search...") },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            Icon(imageVector = Icons.Filled.Face, contentDescription = "Search Icon")
        },
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )


}

@Composable
fun FixRow(items: List<HomeItem>) {

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(items) { item ->
            Column(
                modifier = Modifier.width(120.dp)
            ) {
                fixProduct()
            }
        }
    }

}

@Composable
fun ImageTextGrid(products: List<Product>, columns: Int = 2) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(products.chunked(columns)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (product in rowItems) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        SingleProduct(product)
                    }
                }

                if (rowItems.size < columns) {
                    for (i in rowItems.size until columns) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun SingleProduct(product: Product) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .height(180.dp)
            .width(150.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                product.imageUrls?.let { imageUrls ->
                    ImageSlider(images = imageUrls)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.productTitle ?: "Unknown",
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Price: \$${product.productPrice ?: 0}", fontSize = 10.sp)

                    Button(
                        onClick = {  },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
                    ) {
                        Text(text = "Edit", color = Color.White, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}


@Composable
fun fixProduct(){
    Box(
        modifier = Modifier
            .padding(start = 0.5.dp)
            .height(120.dp)
            .width(90.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.5.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(8.dp),

            ) {
            Column(
                modifier = Modifier.padding(0.2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.amulic1),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.85f)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Amul Packet",
                    fontSize=9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(images: List<String>) {
    val pagerState = rememberPagerState()

    Box {
        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))
        ) { page ->
            Image(
                painter = rememberImagePainter(images[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

