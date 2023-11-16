package com.kotlincocktail.pourpal.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlincocktail.pourpal.R
import com.kotlincocktail.pourpal.ui.theme.Black
import com.kotlincocktail.pourpal.ui.theme.DarkGray
import com.kotlincocktail.pourpal.ui.theme.Gray
import com.kotlincocktail.pourpal.ui.theme.LightGray

@Composable
fun loadingView() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Black)) {
        Card(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(240.dp)
            .align(Alignment.Center),
            colors = CardDefaults.cardColors(DarkGray),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(), // 背景色を設定
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loading_icon),
                    contentDescription = "alt",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
                Text(
                    color = LightGray,
                    text = "メニューを作っています...",
                )
            }
        }
    }
}

@Preview
@Composable
fun preview(){
    loadingView()
}
