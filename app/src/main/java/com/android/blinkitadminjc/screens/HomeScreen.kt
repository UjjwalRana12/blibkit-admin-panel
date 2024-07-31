import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.android.blinkitadminjc.viewmodel.HomeViewModel

@Composable
fun homeScreen(navController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()


    val searchText by homeViewModel.searchText.collectAsState()
    val isSearching by homeViewModel.isSearching.collectAsState()



    Column {
        SearchBar(homeViewModel = homeViewModel)
        Text(text = "Search Text: $searchText")
        Text(text = "Is Searching: $isSearching")
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
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    )
}
