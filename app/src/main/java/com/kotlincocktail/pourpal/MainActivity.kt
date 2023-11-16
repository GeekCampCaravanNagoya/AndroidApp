package com.kotlincocktail.pourpal

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kotlincocktail.pourpal.navigation.Navigation
import com.kotlincocktail.pourpal.ui.theme.PourPalTheme

class MainActivity : ComponentActivity() {
    private lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context  = applicationContext
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, PERMISSIONS,0
            )
        }

        setContent {
            PourPalTheme {
                Navigation(context)
            }
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