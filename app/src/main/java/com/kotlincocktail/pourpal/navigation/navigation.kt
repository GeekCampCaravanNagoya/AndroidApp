package com.kotlincocktail.pourpal.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlincocktail.pourpal.views.ResultListView

@Composable
fun Navigation(applicationContext: Context) {
    val navController = rememberNavController()



    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

//        composable(route = "main") {
//            CameraView(
//                navController=navController,
//                applicationContext = applicationContext
//            )
//        }
//メモ
        composable(route = "main") {
            ResultListView(navController =navController, names = arrayOf("a"))
        }
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

