package com.example.simplestore.redux.state

import com.example.simplestore.model.domain.Filter

data class ProductFilterInfo(
    val filters: Set<Filter> = emptySet(),
    val selectedFilter: Filter? = null,
)
