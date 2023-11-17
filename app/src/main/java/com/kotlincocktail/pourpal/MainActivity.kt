package com.kotlincocktail.pourpal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.kotlincocktail.pourpal.helpers.DatabaseManager
import com.kotlincocktail.pourpal.navigation.Navigation
import com.kotlincocktail.pourpal.ui.theme.PourPalTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // database初期化
        DatabaseManager.initialize(this)

        setContent {
            PourPalTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }

}