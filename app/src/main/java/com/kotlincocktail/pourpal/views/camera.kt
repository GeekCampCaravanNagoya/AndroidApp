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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kotlincocktail.pourpal.viewModels.TakePhoto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraView(
    navController: NavController,
    applicationContext: Context,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    val viewModel = viewModel<TakePhoto>()
    val bitmaps by viewModel.bitmaps.collectAsState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            SheetContent(bitmaps)
        }
    ){ padding ->
        Box(Modifier.fillMaxSize().padding(padding)){
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
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }) { Text(text = "show sheet") }
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
                var bitmap:Bitmap = image.toBitmap()
                bitmap = scaleBitmapDown(bitmap, 640)
                onPhotoTaken(bitmap)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception )
            }
        }
    )
}
private fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
    val originalWidth = bitmap.width
    val originalHeight = bitmap.height
    var resizedWidth = maxDimension
    var resizedHeight = maxDimension
    if (originalHeight > originalWidth) {
        resizedHeight = maxDimension
        resizedWidth =
            (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
    } else if (originalWidth > originalHeight) {
        resizedWidth = maxDimension
        resizedHeight =
            (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
    } else if (originalHeight == originalWidth) {
        resizedHeight = maxDimension
        resizedWidth = maxDimension
    }
    return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
}

@Composable
fun SheetContent(bitmaps: List<Bitmap>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4)
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
    val rememberedBitmap = remember { bitmap }
    Box(modifier = Modifier
        .border(width = 1.dp, color = Color.Gray)
        .clickable { isCheck = !isCheck }){
        Image(
            bitmap = rememberedBitmap.asImageBitmap(),
            contentDescription = ""
        )
        RadioButton(selected = isCheck, onClick = {isCheck = !isCheck})
    }
}

