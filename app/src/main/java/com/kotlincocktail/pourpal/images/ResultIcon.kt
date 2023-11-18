package com.kotlincocktail.pourpal.images

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlincocktail.pourpal.R
import com.kotlincocktail.pourpal.dao.CocktailRecipeWithId

@Composable
fun ResultIcon(recipe: CocktailRecipeWithId) {
    val imageMap = mapOf(
        "カルーアミルク" to mapOf("id" to R.drawable.calua,"contentDescription" to ""),
        "ファジーネーブル" to mapOf("id" to R.drawable.fuzzy,"contentDescription" to ""),
        "ジントニック" to mapOf("id" to R.drawable.ginandtonic,"contentDescription" to ""),
        "moscowmule" to mapOf("id" to R.drawable.moscowmule,"contentDescription" to ""),
    )
    val imageInfo = imageMap[recipe.ingredient_name] ?: mapOf("id" to R.drawable.unknownimage, "contentDescription" to "Default Image")
    Log.d("CocktailRecipeWithId", recipe.toString())
    Column(modifier = Modifier.width(52.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.loading_icon), contentDescription = "")
        Text(
            text = recipe.ingredient_name,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp),
            fontSize = 8.sp,
            lineHeight = 10.sp
        )
    }
}