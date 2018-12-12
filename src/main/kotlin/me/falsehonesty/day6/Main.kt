package me.falsehonesty.day6

import me.falsehonesty.utils.Coord
import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day6in.txt")

    val items = lines.map {
        val split = it.split(", ")
        Coord(split[0].toInt(), split[1].toInt())
    }

    val maxX = items.maxBy { it.first }!!.first
    val maxY = items.maxBy { it.second }!!.second

    /*val maxes = mutableMapOf<Coord, Int>()*/
    val good = mutableListOf<Coord>()

    for (i in 0..maxX) {
        for (j in 0..maxY) {
            val thiz = Coord(i, j)

            val distances = items.associateWith { it.distance(thiz) }

            val max = distances.values.sum()

            if (max < 10000) good.add(thiz)
        }
    }

    /*for (i in 0..maxX) {
        for (j in 0..maxY) {
            val thiz = Coord(i, j)

            val distances = items.associateWith { it.distance(thiz) }

            val smallest = distances.minBy { it.value }!!
            val count = distances.count { it.value == smallest.value }

            if (count > 1) continue

            if (!maxes.containsKey(smallest.key)) maxes[smallest.key] = 0

            maxes[smallest.key] = maxes[smallest.key]!! + 1
        }
    }

    for (i in 0..maxX) {
        val thiz = Coord(i, -1)
        val closest = items.minBy { it.distance(thiz) }!!
        maxes.remove(closest)

        val thiz2 = Coord(i, maxY + 1)
        val closest2 = items.minBy { it.distance(thiz2) }!!
        maxes.remove(closest2)
    }

    for (j in 0..maxY) {
        val thiz = Coord(-1, j)
        val closest = items.minBy { it.distance(thiz) }!!
        maxes.remove(closest)

        val thiz2 = Coord(maxX + 1, j)
        val closest2 = items.minBy { it.distance(thiz2) }!!
        maxes.remove(closest2)
    }*/

//    println(maxes.maxBy { it.value })
    println(good.size)
}


fun Coord.distance(other: Coord): Int {
    return Math.abs(other.first - this.first) + Math.abs(other.second - this.second)
}
