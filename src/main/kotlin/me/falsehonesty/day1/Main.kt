package me.falsehonesty.day1

import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day1in.txt")

    val changes = lines.map {
        it.toInt()
    }

    var freq = 0
    val found = mutableListOf<Int>()
    var ind = 0

    while (true) {
        val change = changes[ind]

        freq += change

        if (found.contains(freq)) {
            println(freq)
            break
        }

        found.add(freq)
        ind++

        if (ind >= changes.size) {
            ind = 0
        }
    }
}
