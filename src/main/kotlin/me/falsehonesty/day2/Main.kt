package me.falsehonesty.day2

import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day2in.txt")

    lines.forEach {
        for (item in lines) {
            var diffs = 0

            if (item == it) continue

            for (i in 0..item.length) {
                try {
                    if (item[i] != it[i]) diffs++
                } catch (e: Exception) {}

                if (diffs > 1) break
            }

            if (diffs <= 1) {
                println("found $it and $item")
            }
        }
    }

//    println("$threes * $twos = ${threes * twos}")
}
