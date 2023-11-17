package com.kotlincocktail.pourpal.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainView(navController: NavHostController) {
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { navController.navigate("camera") }) {
            Text(text = "カメラを起動")
        }
        Button(onClick = { navController.navigate("result/card") }) {
            Text(text = "リザルトを表示")
        }
    }
}
@Preview
@Composable
fun Pre() {
    val  a = rememberNavController()
    MainView(a)
}