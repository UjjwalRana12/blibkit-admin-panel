import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.android.blinkitadminjc.R
import com.android.blinkitadminjc.model.HomeItem
import com.android.blinkitadminjc.viewmodel.HomeViewModel

@Composable
fun homeScreen(navController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()
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
                .height(8.dp)
        )



        FixRow(items = blinkitFoodItems)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        ImageTextGrid(items = blinkitFoodItems, 2)

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
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}

@Composable
fun ImageTextGrid(items: List<HomeItem>, columns: Int = 2) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items.chunked(columns)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (item in rowItems) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(id = item.imageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .height(80.dp)
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.text, maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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