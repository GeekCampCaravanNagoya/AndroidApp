package com.kotlincocktail.pourpal.textrecognize

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText

fun imageAnalyze(image:Bitmap) : Task<FirebaseVisionText> {
    val image = FirebaseVisionImage.fromBitmap(image)
    val detector = FirebaseVision.getInstance().cloudTextRecognizer
    return detector.processImage(image)
        .addOnSuccessListener { firebaseVisionText ->
            firebaseVisionText
        }
        .addOnFailureListener { e ->
            e
        }
}