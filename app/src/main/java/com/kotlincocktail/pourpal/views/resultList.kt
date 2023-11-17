package com.kotlincocktail.pourpal.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kotlincocktail.pourpal.entity.Cocktail
import com.kotlincocktail.pourpal.helpers.DatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ResultListView(
    navController: NavController,
    names: Array<String>
) {

//    deviceのDBから取得する処理
    var cocktails by remember { mutableStateOf(listOf<Cocktail>()) }
    var isLoading by remember { mutableStateOf(true) }

    // Compose特有の非同期処理の記述
    LaunchedEffect(names) {
        val result = withContext(Dispatchers.IO) {
            // 重い処理（例：データベースからのデータ取得）
            val cocktailDao = DatabaseManager.database.CocktailDao()
            cocktailDao.getAll()
        }
        cocktails = result // 結果を状態変数に格納
        isLoading = false
    }
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        CocktailList(cocktails)
        Text("a")
    }

}


@Composable
fun CocktailList(cocktails: List<Cocktail>) {
    LazyColumn {
        items(cocktails) { cocktail ->
            CocktailRow(cocktail.cocktail_name,)
        }
    }
}

@Composable
fun CocktailRow(cocktailName: String) {
    val context = LocalContext.current
    val imageResId = context.resources.getIdentifier("cocktail_$cocktailName", "drawable", context.packageName)
    Row(verticalAlignment = Alignment.CenterVertically) {
        // 画像を表示（ここではURLから画像をロードする例を示します）
        if (imageResId != 0) {
            // 画像リソースが見つかった場合
            val imagePainter = painterResource(id = imageResId)
            Image(
                painter = imagePainter,
                contentDescription = cocktailName,
                modifier = Modifier.size(100.dp)
            )
        } else {
            // デフォルトの画像を表示
//            Image(
//                painter = painterResource(id = R.drawable.default_image),
//                contentDescription = "Default Image",
//                modifier = Modifier.size(100.dp)
//            )
            Text("データがありません")
        }
        Spacer(Modifier.width(8.dp))
        Text(cocktailName)
    }
}