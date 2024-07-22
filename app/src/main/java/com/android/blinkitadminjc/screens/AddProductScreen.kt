package com.android.blinkitadminjc.screens

import android.content.ClipData.Item
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import org.jetbrains.annotations.Async
import java.net.URI

@Composable
fun AddProduct(navController: NavHostController) {

    var productTitleState by remember {
        mutableStateOf("")
    }
    var firstFieldState by remember {
        mutableStateOf("")
    }
    var secondFieldState by remember {
        mutableStateOf("")
    }
    var thirdFieldState by remember {
        mutableStateOf("")
    }
    var fourthFieldState by remember {
        mutableStateOf("")
    }
    var fifthFieldState by remember {
        mutableStateOf("")
    }
    var sixthFieldState by remember {
        mutableStateOf("")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            MyAppBar(title = "Add Product")

            Text(text = "Please fill dish name", fontSize = 22.sp, color = Color.Yellow)

            TextField(
                value = productTitleState,
                onValueChange = { productTitleState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(12.dp)
            ) {
                TextField(
                    value = firstFieldState,
                    onValueChange = { firstFieldState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                TextField(
                    value = secondFieldState,
                    onValueChange = { secondFieldState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(12.dp)
            ) {
                TextField(
                    value = thirdFieldState,
                    onValueChange = { thirdFieldState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
                TextField(
                    value = fourthFieldState,
                    onValueChange = { fourthFieldState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            TextField(
                value = fifthFieldState,
                onValueChange = { fifthFieldState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            TextField(
                value = sixthFieldState,
                onValueChange = { sixthFieldState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            ImagePicker()

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

        LazyRow {
            items(selectedImageURi) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun addProduct() {
    val navController = rememberNavController()
    AddProduct(navController = navController)
}
