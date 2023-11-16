package com.kotlincocktail.pourpal.views

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.kotlincocktail.pourpal.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CameraView(navController: NavHostController) {
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val applicationContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    Scaffold(
        topBar = {
             TopAppBar(
                 title = { Text(text = "メニューを撮影") },
                 navigationIcon = {
                     Icon(
                         imageVector = Icons.Filled.ArrowBackIosNew,
                         contentDescription = "TODO",
                         modifier = Modifier.clickable{
                             navController.navigateUp()
                         }
                     )
                 } //TODO contentDescription
             )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(1f)) {
                        Image(modifier = Modifier.align(Center), painter = painterResource(id = R.drawable.shutter1), contentDescription = "")
                        Image(modifier = Modifier
                            .align(Center)
                            .clip(CircleShape)
                            .clickable {
                                takePhoto(
                                    applicationContext = applicationContext,
                                    controller = controller
                                )
                                navController.navigate("loading")
                            },
                            painter = painterResource(id = R.drawable.shutter2), contentDescription = "")
                    }
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = { showBottomSheet = true }) {
                        Icon(imageVector = Icons.Filled.Collections, contentDescription = "Show Gallery")
                    }
                }
            }
        }
    ){
        Box(modifier = Modifier.padding(it)){
            when {
                permissionState.hasPermission ->
                    AndroidView(
                        factory = {context ->
                            PreviewView(context).apply{
                                this.controller = controller
                                controller.bindToLifecycle(lifecycleOwner)
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                permissionState.shouldShowRationale -> PermissionRationaleDialog {
                    permissionState.launchPermissionRequest()
                }
                permissionState.permissionRequested -> Text("Denied...")
                else -> SideEffect {
                    permissionState.launchPermissionRequest()
                }
            }
            // TODO(AddCameraView)
            if (showBottomSheet) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false}
                ) {
                    Row {
                        TextButton(
                            onClick = { /*TODO*/ }) {
                            Text(text = "すべてのファイル")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        val count = 1
                        Button(
                            modifier = Modifier.padding(bottom = 4.dp, end = 8.dp),
                            onClick = { /*TODO*/ }) {
                            Text(text = "Add ($count)")
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        content = {

                            items(30,key = null) {
                                Image(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    painter = painterResource(id = R.drawable.unknownimage),
                                    contentDescription = "Unknown image"
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun PermissionRationaleDialog(onDialogResult: ()->Unit) {
    AlertDialog(
        text = { Text("Rationale") },
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = onDialogResult) {
                Text("OK")
            }
        }
    )
}
fun takePhoto(
    applicationContext: Context,
    controller: LifecycleCameraController
){
    controller.takePicture(
        ContextCompat.getMainExecutor(applicationContext),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                Log.d("bitmap", "onTakePhoto: ")
                var bitmap: Bitmap = image.toBitmap()
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception )
            }
        }
    )
}