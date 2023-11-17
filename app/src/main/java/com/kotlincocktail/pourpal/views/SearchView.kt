package com.kotlincocktail.pourpal.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kotlincocktail.pourpal.ui.theme.DarkBlue
import com.kotlincocktail.pourpal.ui.theme.DarkGray
import com.kotlincocktail.pourpal.ui.theme.Gray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(20.dp)
    ) {
        var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("お酒の名前を入力", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(15),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = DarkGray,
                unfocusedPlaceholderColor = Gray,
                focusedPlaceholderColor = Gray,
                cursorColor = Gray,
                focusedIndicatorColor = Gray,
                unfocusedIndicatorColor = Gray,
                focusedTextColor = Gray,
                unfocusedTextColor = Gray,
            ),
            singleLine = true,
        )

    }
}