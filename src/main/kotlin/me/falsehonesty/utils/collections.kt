package me.falsehonesty.utils

fun <T> Iterable<T>.cycle() = generateSequence { this }.flatten()

fun <T> Collection<T>.eachCount() = this.groupingBy { it }.eachCount()
