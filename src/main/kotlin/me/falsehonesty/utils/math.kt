package me.falsehonesty.utils

typealias Coord = Pair<Int, Int>

fun Int.exceeds(max: Int, min: Int = 0): Boolean {
    return this < min || this > max
}

fun Coord.exceeds(max: Int, min: Int = 0): Boolean {
    return first.exceeds(max, min) || second.exceeds(max, min)
}
