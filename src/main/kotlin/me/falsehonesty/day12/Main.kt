package me.falsehonesty.day12

import me.falsehonesty.utils.resource
import kotlin.math.absoluteValue

fun stringToBool(string: String): List<Boolean> {
    return string.toCharArray().map {
        when(it) {
            '#' -> true
            '.' -> false
            else -> null
        }
    }.filterNotNull()
}

fun main() {
    val lines = resource("/day12in.txt")
    val data = "#..#.#..##......###...###"//"#..######..#....#####..###.##..#######.####...####.##..#....#.##.....########.#...#.####........#.#."

    val patterns = lines.map {
        val (from, to) = it.split(" => ")

        Pattern(
            stringToBool(from),
            to == "#"
        )
    }

    val right = stringToBool(data).toMutableList()

    val left = mutableListOf<Boolean>()

    fun access(index: Int): Boolean {
        return if (index < 0) {
            val abs = index.absoluteValue

            if (abs >= left.size) {
                false
            } else {
                left[abs - 1]
            }
        } else {
            if (index >= right.size) {
                false
            } else {
                right[index]
            }
        }
    }

    fun ensureCapacity(list: MutableList<Boolean>, value: Boolean, size: Int) {
        while (list.size < size) {
            list.add(value)
        }
    }

    fun set(index: Int, boolean: Boolean) {
        if (index < 0) {
            val real = index.absoluteValue
            ensureCapacity(left, false, real)
            left[real - 1] = boolean
        } else {
            ensureCapacity(right, false, index + 1)
            right[index] = boolean
        }
    }

    fun leftmost(): Int {
        val leftI = left.lastIndexOf(true)

        return (if (leftI == -1) right.indexOfFirst { true } else -(leftI + 1)) - 3
    }

    fun rightmost(): Int {
        val rightI = right.lastIndexOf(true)

        return (if (rightI == -1) -(left.indexOfFirst { true } + 1) else rightI) + 3
    }

    repeat(20) { i ->
        for (x in leftmost()..rightmost()) {
            val around = ((x - 2)..(x + 2)).map { access(it) }

            val matched = patterns.find {
                it.from == around
            }

            if (matched != null) {
                set(x - 2, false)
                set(x - 1, false)
                set(x, matched.to)
                set(x + 1, false)
                set(x + 2, false)
            }
        }


        println(
            (left.reversed() + right).joinToString(separator = "") { if (it) "#" else "." }
        )
    }

    val sum = (leftmost()..rightmost()).map { access(it) }.count { it }
    println(sum)
}

data class Pattern(val from: List<Boolean>, val to: Boolean)
