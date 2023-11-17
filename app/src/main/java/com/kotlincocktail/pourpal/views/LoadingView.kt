package com.kotlincocktail.pourpal.views

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import com.kotlincocktail.pourpal.R
import com.kotlincocktail.pourpal.entity.Cocktail
import com.kotlincocktail.pourpal.helpers.DatabaseManager
import com.kotlincocktail.pourpal.ui.theme.Black
import com.kotlincocktail.pourpal.ui.theme.DarkGray
import com.kotlincocktail.pourpal.ui.theme.LightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalGetImage::class) @Composable
fun LoadingView(
    navController: NavHostController,
    imageProxy: ImageProxy?,
    resultList: (List<Cocktail>) -> Unit
) {
    val textRecognizer: TextRecognizer by lazy {
        TextRecognition.getClient(
            JapaneseTextRecognizerOptions.Builder().build())
    }
    LaunchedEffect(imageProxy) {
        if (imageProxy != null) {
            imageProxy.image?.let { mediaImage ->
                val image = InputImage.fromMediaImage(
                    mediaImage, imageProxy.imageInfo.rotationDegrees)
                textRecognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        // 解析結果
                        Log.d("OCR", visionText.text)
                        val cocktailNames = visionText.text.split("\n")
                        // DBから取得
                        CoroutineScope(Dispatchers.IO).launch {
                            val cocktailDao = DatabaseManager.database.CocktailDao()
                            val cocktailRecipeDao = DatabaseManager.database.CocktailRecipeDao()
                            // 取得結果
                            val cocktails = cocktailDao.findCocktailsByName(cocktailNames)
                            val cocktailIds = cocktails.map { it.cocktail_id }.toIntArray()
                            val recipes = cocktailRecipeDao.getCocktailRecipesWithJoin(cocktailIds)
                            resultList(cocktails)
                        }
                    }
                    .addOnFailureListener { exc ->
                        Log.e("OCR", "認識に失敗しました$exc")
                    }
                    .addOnCompleteListener {
                        // 認識が終わったら、画像を解放する
                        imageProxy.close()
                        navController.navigate("result") {
                            popUpTo("camera") { inclusive = true }
                        }
                    }
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Black)) {
        Card(
            modifier = Modifier
                .clickable { navController.navigate("result") }//TODO delete clickable()
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