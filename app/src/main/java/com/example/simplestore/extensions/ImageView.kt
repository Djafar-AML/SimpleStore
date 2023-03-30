package com.example.simplestore.extensions

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.transform.CircleCropTransformation
import com.example.simplestore.R


fun ImageView.loadByCoil(imageUrl: String, placeholder: Int = R.drawable.ic_image_placeholder) {

    this.load(imageUrl) {
        crossfade(true)
        placeholder(placeholder)
        transformations(CircleCropTransformation())
    }

}

fun ImageView.loadByCoil(
    imageUrl: String,
    placeholder: Int = R.drawable.ic_image_placeholder,
    coilListener: (ImageRequest, ImageResult) -> Unit
) {


    this.load(imageUrl) {
        crossfade(true)
        placeholder(placeholder)
        transformations(CircleCropTransformation())
        listener { request, result ->
            coilListener(request, result)
        }
    }

}

fun ImageView.drawableRes(drawableIV: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, drawableIV))
}
