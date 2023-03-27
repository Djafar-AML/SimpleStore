package com.example.simplestore.extensions

import android.widget.ImageView
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
