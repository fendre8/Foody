package com.example.foody.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.foody.R

@Preview
@Composable
fun About(pressOnBack: () -> Unit = {}) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(ScrollState(0), true)
    ) {
        Text(text = "About us", fontSize = 35.sp, modifier = Modifier.padding(0.dp, 30.dp))
        Image(
            painter = painterResource(R.drawable.meal_icon), contentDescription = "Meal icon",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = "Foody",
            fontSize = 35.sp,
            fontWeight = FontWeight(500),
            letterSpacing = TextUnit(0.5F, TextUnitType.Em)
        )
        Image(
            painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/zag2me/script.screensaver.themealdb/master/icon.png"),
            contentDescription = "TheMealDb",
            modifier = Modifier
                .size(200.dp)
                .padding(0.dp, 20.dp)
        )
        Text(
            text = "TheMealDB was built in 2016 to provide a free data source api for recipes online " +
                    "in the hope that developers would build applications and cool projects ontop of it. " +
                    "TheMealDB originated on the Kodi forums as a way to browse recpies on your TV.",
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(30.dp, 0.dp)
        )
    }
}