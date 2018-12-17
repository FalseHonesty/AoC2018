package me.falsehonesty.day10

import me.falsehonesty.utils.XY
import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day10in.txt")
    val regex = """position=<\s*(-?\d+),\s*(-?\d+)> velocity=<\s*(-?\d+),\s*(-?\d+)>""".toRegex()

    val points = lines.map {
        val (x1, y1, x2, y2) = regex.matchEntire(it)!!.groupValues.drop(1).map { it.trim().toInt() }

        Point(XY(x1, y1), XY(x2, y2))
    }

    val map = points.associateBy { it.position }.toMutableMap()

    var seconds = 0
    var first = true

    while (true) {
        var linedUp = 0

        for (point in points) {
            point.tick(map)

            val pos = point.position

            if (around(map, pos)) {
                linedUp++
            }
        }

        if (linedUp > points.size / 2) {
//            printPoints(map)
            if (first) {
                first = false
            } else {
                println("FOUND IT")
                printPoints(map)
                println(seconds)
            }
        }

        seconds++
    }
}

fun around(map: Map<XY, Point>, pos: XY): Boolean {
    val up = pos.off(y = -1)
    val down = pos.off(y = 1)
    val right = pos.off(x = 1)
    val left = pos.off(x = -1)

    when {
        map[up] != null -> Unit/*println("Found: ${map[up]}")*/
        map[down] != null -> Unit/*println("Found: ${map[down]}")*/
        map[left] != null -> Unit/*println("Found: ${map[left]}")*/
        map[right] != null -> Unit/*println("Found: ${map[right]}")*/
        else -> return false
    }

    return true
}

fun printPoints(map: Map<XY, Point>) {
    val points = map.values

    val minX = points.minBy { it.position.x }!!.position.x
    val minY = points.minBy { it.position.y }!!.position.y
    val maxX = points.maxBy { it.position.x }!!.position.x
    val maxY = points.maxBy { it.position.y }!!.position.y

    println("--------POSSIBLE MATCH--------")
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            val xy = XY(x, y)

            if (map[xy] == null) {
                print(".")
            } else {
                print("#")
            }
        }

        println()
    }
}

data class Point(var position: XY, val velocity: XY) {
    fun tick(map: MutableMap<XY, Point>) {
        map.remove(this.position)
        this.position = XY(this.position.x + velocity.x, this.position.y + velocity.y)
        map[this.position] = this
    }
}
