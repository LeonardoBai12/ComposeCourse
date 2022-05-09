package io.lb.composetrivia.model

import kotlin.collections.ArrayList

data class Question(
    val question: String,
    val category: String,
    val answer: String,
    val choices: List<String>
)