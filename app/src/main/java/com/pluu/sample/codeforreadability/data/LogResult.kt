package com.pluu.sample.codeforreadability.data

data class LogResult(
    val login: String,
    val age: String = "999",
    val contribute: List<String>
)