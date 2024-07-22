package com.android.blinkitadminjc.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.android.blinkitadminjc.model.Product
import com.android.blinkitadminjc.viewmodel.AdminViewModel
import com.android.blinkitjc.utils.Utils
import java.util.UUID


@Composable
fun AddProduct(navController: NavHostController) {

    val adminViewModel :AdminViewModel = viewModel()
    val imageURIs: ArrayList<Uri> = arrayListOf()


    var productTitleState by remember {
        mutableStateOf("")
    }
    var quantityState by remember {
        mutableStateOf("")
    }
    var unitState by remember {
        mutableStateOf("")
    }
    var priceState by remember {
        mutableStateOf("")
    }
    var numberStockState by remember {
        mutableStateOf("")
    }
    var productCategoryState by remember {
        mutableStateOf("")
    }
    var productTypeState by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            MyAppBar(title = "Add Product")

            Text(text = "Please fill dish name", fontSize = 22.sp, color = Color.Yellow)

            TextField(
                value = productTitleState,
                placeholder = { Text(text = "Product Title") },
                onValueChange = { productTitleState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(2.dp)
            ) {
                TextField(
                    value = quantityState,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text(text = "Quantity ") },
                    onValueChange = { quantityState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                TextField(
                    value = unitState,
                    placeholder = { Text(text = "Unit") },
                    onValueChange = { unitState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(2.dp)
            ) {
                TextField(
                    value = priceState,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text(text = "Price") },
                    onValueChange = { priceState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                TextField(
                    value = numberStockState,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text(text = "no. of Stock") },
                    maxLines = 1,
                    singleLine = true,
                    onValueChange = { numberStockState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            TextField(
                value = productCategoryState,
                placeholder = { Text(text = "Product Category") },
                onValueChange = { productCategoryState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )

            TextField(
                value = productTypeState,
                placeholder = { Text(text = "Product Type") },
                onValueChange = { productTypeState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )

            ImagePicker()



            Button(onClick = {
                val product = Product(
                    productRandomId = UUID.randomUUID().toString(),
                    productQuantity = quantityState.toIntOrNull(),
                    productUnit = unitState,
                    productPrice = priceState.toIntOrNull(),
                    productStock = numberStockState.toIntOrNull(),
                    productCategory = productCategoryState,
                    productType = productTypeState,
                    productTitle = productTitleState,
                    adminUID = Utils.getCurrentUserID(),
                    itemCount = 0,
                )

            }) {
                Text(text = "Add Products")
            }
        }
    }
}



@Composable
fun ImagePicker() {
    var selectedImageURi by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList ->
            selectedImageURi = uriList
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextButton(onClick = {
            imagePickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text(text = "Please select Some Images")
        }

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)) {
            items(selectedImageURi) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .width(100.dp)
                        .height(100.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun addProduct() {
    val navController = rememberNavController()
    val adminViewModel: AdminViewModel = viewModel()

    AddProduct(navController = navController)
}


