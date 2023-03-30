package com.example.simplestore.model.ui

import com.example.simplestore.model.domain.Filter

data class UiFilter(
    val filter: Filter,
    val isSelected: Boolean,
)
