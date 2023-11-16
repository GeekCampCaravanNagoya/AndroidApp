package com.kotlincocktail.pourpal.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlincocktail.pourpal.views.CameraView
import com.kotlincocktail.pourpal.views.maintest

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "camera"
    ) {

        composable(route = "main") {
            maintest()
        }
        composable(route = "camera") {
            CameraView()
        }
//メモ
//        composable(route = "page2") {
//            page2(navController)
//        }
//        composable(
//            route = "page3/{id}/{title}",
//            arguments = listOf(
//                navArgument("id") { type = NavType.IntType },
//                navArgument("title") { type = NavType.StringType }
//            )
//        ){arguments ->
//            val id = arguments.arguments?.getInt("id") ?: ""
//            val title = arguments.arguments?.getString("title") ?: ""
//            page3(id,title)
//        }
    }
}