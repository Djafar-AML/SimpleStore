package com.example.simplestore.extensions

import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView


fun MaterialCardView.setCustomBackgroundColor(backgroundColor: Int) {
    this.setCardBackgroundColor(ContextCompat.getColor(context, backgroundColor))
}
