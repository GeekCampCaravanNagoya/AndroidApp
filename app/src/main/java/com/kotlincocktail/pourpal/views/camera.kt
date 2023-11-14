package com.kotlincocktail.pourpal.views

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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kotlincocktail.pourpal.R
import com.kotlincocktail.pourpal.viewModels.TakePhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraView(
    navController: NavController,
    applicationContext: Context,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    val viewModel = viewModel<TakePhoto>()
    val bitmaps by viewModel.bitmaps.collectAsState()

    AndroidView(
        factory = {
            PreviewView(it).apply{
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
    Row {
        Button(onClick = {
            showBottomSheet = true
        }) { Text(text = "show sheet") }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                SheetContent(bitmaps)
            }
        }
        IconButton(onClick = {
            takePhoto(
                applicationContext = applicationContext,
                controller = controller,
                onPhotoTaken = viewModel::onTakePhoto
            )
        }) {
            Icon(Icons.Filled.Camera,"")
        }
    }



}
fun takePhoto(
    applicationContext: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
){
    controller.takePicture(
        ContextCompat.getMainExecutor(applicationContext),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                Log.d("bitmap", "onTakePhoto: ")
                onPhotoTaken(image.toBitmap())
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception )
            }
        }
    )
}

@Composable
fun SheetContent(bitmaps: List<Bitmap>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5)
    ) {
        Log.d("bitmaps", "bitmaps: "+bitmaps.size)
        items(bitmaps){
            Gallery(it)
        }
    }
}

@Composable
fun Gallery(bitmap: Bitmap) {
    var isCheck  by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .border(width = 1.dp, color = Color.Gray)
        .clickable { isCheck = !isCheck }){
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = ""
        )
        RadioButton(selected = isCheck, onClick = {isCheck = !isCheck})
    }
}

