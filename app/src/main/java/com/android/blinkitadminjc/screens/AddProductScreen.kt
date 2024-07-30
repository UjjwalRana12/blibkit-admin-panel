package com.android.blinkitadminjc.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct(navController: NavHostController) {
    val adminViewModel: AdminViewModel = viewModel()
    val context = LocalContext.current

    val list = listOf("one", "two", "three")
    val list_one = listOf("one", "two", "three")
    val list_two = listOf("one", "two", "three")

    var isExpandable by remember { mutableStateOf(false) }
    var isExpandable_one by remember { mutableStateOf(false) }
    var isExpandable_two by remember { mutableStateOf(false) }


    var productTitleState by remember { mutableStateOf("") }
    var quantityState by remember { mutableStateOf("") }
    var unitState by remember { mutableStateOf("") }
    var priceState by remember { mutableStateOf("") }
    var numberStockState by remember { mutableStateOf("") }
    var productCategoryState by remember { mutableStateOf(list[0]) }
    var productTypeState by remember { mutableStateOf("") }
    var selectedImageUris by remember { mutableStateOf(emptyList<Uri>()) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    fun validateInputs(): Boolean {
        return productTitleState.isNotBlank() && quantityState.isNotBlank() && unitState.isNotBlank() &&
                priceState.isNotBlank() && numberStockState.isNotBlank() && productCategoryState.isNotBlank() &&
                productTypeState.isNotBlank() && selectedImageUris.isNotEmpty()
    }

    LaunchedEffect(key1 = adminViewModel.isImageUploaded.collectAsState().value) {
        if (adminViewModel.isImageUploaded.value) {
            showLoadingDialog = false
            Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier=Modifier.verticalScroll(rememberScrollState())) {
            MyAppBar(title = "Add Product")

            Text(text = "Please fill product details", fontSize = 22.sp, color = Color.Yellow)

            TextField(
                value = productTitleState,
                placeholder = { Text(text = "Product Title") },
                onValueChange = { productTitleState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            )


            TextField(
                value = quantityState,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "Quantity") },
                onValueChange = { quantityState = it },
                modifier = Modifier
                    .padding(8.dp)
            )

            ExposedDropdownMenuBox(expanded = isExpandable_two, onExpandedChange = {
                isExpandable_two = !isExpandable_two
            }) {

                TextField(
                    value = unitState,
                    placeholder = { Text(text = "Unit") },
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandable) },
                    modifier = Modifier
                        .padding(8.dp)
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isExpandable_two,
                    onDismissRequest = { isExpandable_two = false }) {
                    list.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text) },
                            onClick = {
                                unitState = list_two[index]
                                isExpandable_two = false

                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }

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
                    placeholder = { Text(text = "No. of Stock") },
                    maxLines = 1,
                    singleLine = true,
                    onValueChange = { numberStockState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            ExposedDropdownMenuBox(expanded = isExpandable, onExpandedChange = {
                isExpandable = !isExpandable
            }) {
                TextField(
                    value = productCategoryState,
                    placeholder = { Text(text = "Product Category") },
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandable) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isExpandable,
                    onDismissRequest = { isExpandable = false }) {
                    list.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text) },
                            onClick = {
                                productCategoryState = list[index]
                                isExpandable = false

                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(expanded = isExpandable_one, onExpandedChange = {
                isExpandable_one = !isExpandable_one
            }) {
                TextField(
                    value = productTypeState,
                    placeholder = { Text(text = "Product Type") },
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpandable_one) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .menuAnchor()

                )
                ExposedDropdownMenu(
                    expanded = isExpandable_one,
                    onDismissRequest = { isExpandable_one = false }) {
                    list.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            text = { Text(text) },
                            onClick = {
                                productTypeState = list_one[index]
                                isExpandable_one = false

                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }

            }
            ImagePicker(onImagesSelected = { uris ->
                selectedImageUris = uris
            })

            Button(onClick = {
                if (validateInputs()) {
                    showLoadingDialog = true

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

                    adminViewModel.saveImageInDB(selectedImageUris)
                    adminViewModel.saveProduct(product)
                } else {
                    Toast.makeText(
                        context,
                        "Please fill all fields and select images",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(text = "Add Product")
            }
        }

        if (showLoadingDialog) {
            Utils.LoadingDialog(
                message = "Uploading images and saving product...",
                onDismissRequest = {
                    showLoadingDialog = false
                })
        }
    }
}


@Composable
fun ImagePicker(onImagesSelected: (List<Uri>) -> Unit) {
    var selectedImageURi by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uriList ->
            selectedImageURi = uriList
            onImagesSelected(uriList)
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

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
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
fun AddProductPreview() {
    val navController = rememberNavController()
    AddProduct(navController = navController)
}
