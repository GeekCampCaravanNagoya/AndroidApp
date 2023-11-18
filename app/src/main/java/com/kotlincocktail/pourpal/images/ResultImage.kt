package com.kotlincocktail.pourpal.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kotlincocktail.pourpal.R

@Composable
fun ResultImage(key: String) {
    val imageMap = mapOf(
        "カルーアミルク" to mapOf("id" to R.drawable.calua,"contentDescription" to ""),
        "ファジーネーブル" to mapOf("id" to R.drawable.fuzzy,"contentDescription" to ""),
        "ジントニック" to mapOf("id" to R.drawable.ginandtonic,"contentDescription" to ""),
        "moscowmule" to mapOf("id" to R.drawable.moscowmule,"contentDescription" to ""),
    )
    val imageInfo = imageMap[key] ?: mapOf("id" to R.drawable.unknownimage, "contentDescription" to "Default Image")
    Image(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        painter = painterResource(id = imageInfo["id"] as Int),
        contentDescription = imageInfo["contentDescription"] as String
    )
}