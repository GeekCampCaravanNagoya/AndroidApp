package com.kotlincocktail.pourpal

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kotlincocktail.pourpal.navigation.Navigation
import com.kotlincocktail.pourpal.ui.theme.PourPalTheme

class MainActivity : ComponentActivity() {

    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context  = applicationContext
        setContent {
            PourPalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }

    @Composable
    fun AllowPermission(){
        if(!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, PERMISSIONS,0
            )
        }
    }
    private fun hasRequiredPermissions():Boolean{
        return PERMISSIONS.all{
            ContextCompat.checkSelfPermission(
                context,
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