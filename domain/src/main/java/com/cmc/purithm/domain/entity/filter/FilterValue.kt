package com.cmc.purithm.domain.entity.filter

data class FilterValue(
    val lightBalance : Int = 0,
    val brightness : Int = 0,
    val exposure : Int = 0,
    val contrast : Int = 0,
    val highlight : Int = 0,
    val shadow : Int = 0,
    val saturation : Int = 0,
    val tint : Int = 0,
    val temperature : Int = 0,
    val clear : Int = 0,
    val clarity : Int = 0
)