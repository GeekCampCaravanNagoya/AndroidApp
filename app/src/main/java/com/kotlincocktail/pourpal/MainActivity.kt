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

        try {
            val inputStream = assets.open("jpn.traineddata")
            val tessdataDir = File(applicationContext.filesDir, "tessdata")
            if (!tessdataDir.exists()) {
                tessdataDir.mkdir()
            }

            val outputStream = FileOutputStream(File(tessdataDir, "jpn.traineddata"))

            val buffer = ByteArray(1024)
            var length = 0
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            inputStream.close()
            outputStream.close()
        }catch (e:Exception){
            e.stackTrace
        }

        setContent {
            PourPalTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }

}