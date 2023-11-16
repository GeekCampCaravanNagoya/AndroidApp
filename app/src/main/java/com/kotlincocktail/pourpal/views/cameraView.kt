package com.kotlincocktail.pourpal.views

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    Scaffold(
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
                permissionState.hasPermission -> Text("Granted!")
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