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
                        //Log.d("OCR", visionText.text)
                        // visionText.textの中の空白を改行に変換
                        val cocktailNames = visionText.text.replace(" ", "\n").split("\n").filter { it.toIntOrNull() == null }

                        // 漢字の一をーに変換
                        val modifiedCocktailNames = cocktailNames.map { it.replace("一", "ー") }
                        // 各要素から数字部分と英語を削除
                        val modifiedCocktailNames0 = modifiedCocktailNames.map { it.replace(Regex("[0-9a-zA-Z]"), "") }
                        // ()と…と.を削除
                        val modifiedCocktailNames1 = modifiedCocktailNames0.map { it.replace(Regex("[()….]"), "") }
                        // 円または税込を含んでいたら削除
                        val modifiedCocktailNames2 = modifiedCocktailNames1.map { it.replace(Regex("円|税込"), "") }
                        // 空白データを削除
                        val modifiedCocktailNames3 = modifiedCocktailNames2.filter { it != "" }
                        Log.d("OCR333", modifiedCocktailNames3.toString())

                        fun levenshteinDistance(s1: String, s2: String): Int {
                            val m = s1.length
                            val n = s2.length
                            val dp = Array(m + 1) { IntArray(n + 1) }
                        
                            for (i in 0..m) {
                                for (j in 0..n) {
                                    if (i == 0 || j == 0) {
                                        dp[i][j] = i + j
                                    } else {
                                        dp[i][j] = minOf(
                                            dp[i - 1][j] + 1,
                                            dp[i][j - 1] + 1,
                                            dp[i - 1][j - 1] + if (s1[i - 1] == s2[j - 1]) 0 else 1
                                        )
                                    }
                                }
                            }
                        
                            return dp[m][n]
                        }

                        // データベースから取得する処理
                        CoroutineScope(Dispatchers.IO).launch {
                            val cocktailDao = DatabaseManager.database.CocktailDao()
                            val cocktailRecipeDao = DatabaseManager.database.CocktailRecipeDao()

                            // 検索対象の名前リスト
                            val searchNames = modifiedCocktailNames3.distinct() // 重複を除去

                            // 2文字以内の誤差でヒットするカクテルを格納するリスト
                            val matchedCocktails = mutableListOf<Cocktail>()

                            // データベースから全てのカクテルを取得
                            val allCocktails = cocktailDao.getAll()

                            for (name in modifiedCocktailNames) {
                                for (cocktail in allCocktails) {
                                    // 2文字以内の誤差でヒットするカクテルを抽出
                                    if (levenshteinDistance(name, cocktail.cocktail_name) <= 2) {
                                        matchedCocktails.add(cocktail)
                                    }
                                }
                            }

                            val uniqueMatchedCocktails = matchedCocktails.toSet().toList()
                            val cocktailIds = uniqueMatchedCocktails.map { it.cocktail_id }.toIntArray()
                            val recipes = cocktailRecipeDao.getCocktailRecipesWithJoin(cocktailIds)
                            resultList(uniqueMatchedCocktails)
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