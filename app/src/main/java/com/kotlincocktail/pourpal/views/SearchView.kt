package com.kotlincocktail.pourpal.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kotlincocktail.pourpal.dao.CocktailRecipeWithId
import com.kotlincocktail.pourpal.entity.Cocktail
import com.kotlincocktail.pourpal.helpers.DatabaseManager
import com.kotlincocktail.pourpal.ui.theme.DarkBlue
import com.kotlincocktail.pourpal.ui.theme.DarkGray
import com.kotlincocktail.pourpal.ui.theme.Gray
import com.kotlincocktail.pourpal.ui.theme.LightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private fun placeHolderVisualTransformation(
    placeholder: String,
): VisualTransformation = VisualTransformation { text ->
    val showPlaceHolder = text.text.isEmpty()
    TransformedText(
        AnnotatedString(text = text.text.ifEmpty { " $placeholder " }),
        object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int =
                if (showPlaceHolder) 0 else offset

            override fun transformedToOriginal(offset: Int): Int =
                if (showPlaceHolder) 0 else offset
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    navController: NavController,
    cocktails: (List<Cocktail>) -> Unit,
    recipes: (List<CocktailRecipeWithId>) -> Unit){


var nickname: TextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    val showPlaceholder: Boolean by remember(nickname.text) {
        mutableStateOf(nickname.text.isEmpty())
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(20.dp)
    ) {
        Text(
            text = "今日は何にしましょうか？",
            color = LightGray,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
        TextField(
            value = nickname,
            onValueChange = {
                    textFieldValue -> nickname = textFieldValue

                    },
            placeholder = { Text("お酒の名前を入力", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
            modifier = Modifier
                .width(320.dp)
                .align(Alignment.BottomStart),
            shape = RoundedCornerShape(15),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DarkGray,
                unfocusedPlaceholderColor = Gray,
                focusedPlaceholderColor = Gray,
                cursorColor = LightGray,
                focusedIndicatorColor = Gray,
                unfocusedIndicatorColor = Gray,
                focusedTextColor = if (showPlaceholder) LightGray else DarkGray,
            ),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                color = LightGray
            ),
            visualTransformation = placeHolderVisualTransformation("お酒の名前を入力")
        )
        
        Button(
            onClick = {
                val cocktailNames = nickname.text.split("\n")
                // DBから取得
                CoroutineScope(Dispatchers.IO).launch {
                    val cocktailDao = DatabaseManager.database.CocktailDao()
                    val cocktailRecipeDao = DatabaseManager.database.CocktailRecipeDao()
                    // 取得結果
                    val cocktails = cocktailDao.findCocktailsByName(cocktailNames)
                    val cocktailIds = cocktails.map { it.cocktail_id }.toIntArray()
                    val recipes = cocktailRecipeDao.getCocktailRecipesWithJoin(cocktailIds)
                    cocktails(cocktails)
                    recipes(recipes)
                }
                navController.navigate("result"){
                    popUpTo("search") { inclusive = true }
                }},
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGray,
                contentColor = LightGray
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(
                    color = DarkGray,
                    shape = RoundedCornerShape(20)
                )
                .height(TextFieldDefaults.MinHeight),
            contentPadding = PaddingValues(5.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.size(40.dp),
                tint = LightGray
            )
            Text(text = "検索")
        }

    }
}