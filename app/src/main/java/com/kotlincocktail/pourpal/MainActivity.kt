package com.kotlincocktail.pourpal

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.ImageReader
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kotlincocktail.pourpal.helpers.DatabaseManager
import com.kotlincocktail.pourpal.navigation.Navigation
import com.kotlincocktail.pourpal.ui.theme.PourPalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, PERMISSIONS,0
            )
        }
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

    private fun hasRequiredPermissions():Boolean{
        return PERMISSIONS.all{
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
    }

}

