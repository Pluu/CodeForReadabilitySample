package com.pluu.sample.codeforreadability.model

data class SampleItem(
    val text: String,
    // FIXED 12. modify instance type
    val bgColor: ColorValue,
    // FIXED 7. add field favorite
    val isFavorite: Boolean = false,
    // FIXED 8. add field action
    val onFavorite: (String) -> Unit
)