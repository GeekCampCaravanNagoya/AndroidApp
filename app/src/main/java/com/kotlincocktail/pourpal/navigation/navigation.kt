package com.kotlincocktail.pourpal.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlincocktail.pourpal.views.ResultListView
import com.kotlincocktail.pourpal.views.CameraView
import com.kotlincocktail.pourpal.views.HomeView
import com.kotlincocktail.pourpal.views.MainView
import com.kotlincocktail.pourpal.views.LoadingView
import com.kotlincocktail.pourpal.views.ResultCardView

@Composable
fun Navigation() {
    val navController = rememberNavController()



    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

//        composable(route = "main") {
//            CameraView(
//                navController=navController,
//                applicationContext = applicationContext
//            )
//        }
//メモ
        composable(route = "home"){
            HomeView(navController=navController)
        }

        composable(route = "main") {
            MainView(navController=navController)
        }
        composable(route = "camera") {
            CameraView(navController=navController)
        }
        composable(route = "loading") {
            LoadingView(navController=navController)
        }
        composable(route = "result/card") {
            ResultCardView()
        }
        composable(route = "result/list") {
            ResultListView(navController =navController, names = arrayOf("a"))
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

