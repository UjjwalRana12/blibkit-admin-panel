package com.android.blinkitadminjc.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.blinkitadminjc.R
import com.android.blinkitjc.model.Dish

@Composable
fun illustrationImage(modifier: Modifier, @DrawableRes drawable: Int) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = "illustration chef image",
            modifier = modifier
        )

    }
}

@Composable
fun SearchBar(

    onSearch: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.Yellow)
            .border(1.dp, Color.Gray, MaterialTheme.shapes.medium)
            .padding(5.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )

            // Search Input Field
            val (text, setText) = remember { mutableStateOf("") }
            BasicTextField(
                value = text, onValueChange = { newText ->
                    setText(newText)
                    onSearch(newText)
                },

                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)

            )
            if (text.isNotEmpty()) {
                IconButton(
                    onClick = { setText("") }, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Color.Black,
                    )
                }
            }
        }
    }
}

@Composable
fun DishCard(dish: List<Dish>) {

    Box(modifier = Modifier.wrapContentSize(), Alignment.Center) {


        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blinkit_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

            }
            Text(text = "this is dish name", fontSize = 12.sp)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        modifier = Modifier.fillMaxWidth(),

    )
}