package com.kotlincocktail.pourpal.views

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainView(navController: NavHostController) {
    Button(onClick = { navController.navigate("camera") }) {
        Text(text = "カメラを起動")
    }
}