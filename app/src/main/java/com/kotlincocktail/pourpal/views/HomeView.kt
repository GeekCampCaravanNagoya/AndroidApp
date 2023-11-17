package com.kotlincocktail.pourpal.views

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.WineBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kotlincocktail.pourpal.R
import com.kotlincocktail.pourpal.ui.theme.DarkBlue
import com.kotlincocktail.pourpal.ui.theme.DarkGray
import com.kotlincocktail.pourpal.ui.theme.Gray


@Composable
fun HomeView(navController: NavController) {


    // バックグラウンドを塗りつぶす
    Box(modifier = Modifier
        .fillMaxSize()
        .background(DarkBlue)
        .padding(30.dp)) {
        // テキストを中央に配置
        Text(
            text = "メニューを選択して、\nお気に入りの世界を探索しよう。",
            color = Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)

        )
        //カメラボタン
        Button(
            onClick = { navController.navigate("main") },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGray,
                contentColor = Gray
            ),
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = DarkGray,
                    shape = RoundedCornerShape(20)
                ),
            contentPadding = PaddingValues(0.dp)


        ) {
            Icon(
                imageVector = Icons.Filled.CameraAlt,
                contentDescription = "camera",
                modifier = Modifier.size(40.dp),
                tint = DarkBlue
            )

        }
        //検索ボタン
        Button(
            onClick = { navController.navigate("main") },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGray,
                contentColor = Gray
            ),
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .align(Alignment.BottomStart)
                .background(
                    color = DarkGray,
                    shape = RoundedCornerShape(30)
                ),
            contentPadding = PaddingValues(0.dp)



        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.size(30.dp),

                tint = DarkBlue
            )

        }

        //キープボトルボタン
        Button(
            onClick = { navController.navigate("main") },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGray,
                contentColor = Gray
            ),
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .align(Alignment.BottomEnd)
                .background(
                    color = DarkGray,
                    shape = RoundedCornerShape(30)
                ),
            contentPadding = PaddingValues(0.dp)



        ) {
            Icon(
                imageVector = Icons.Filled.WineBar,
                contentDescription = "WineBar",
                modifier = Modifier.size(30.dp),

                tint = DarkBlue
            )
        }
    }
}