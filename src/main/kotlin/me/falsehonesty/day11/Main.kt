package me.falsehonesty.day11

import me.falsehonesty.utils.XY

const val serial = 3613

fun main() {
    val grid = Array(300) { IntArray(300) }
    val pre = Array(300) { IntArray(300) }

    for (x in 0 until grid.size) {
        for (y in 0 until grid[0].size) {
            val rack = x + 10
            var num = (rack * y).toLong()

            num += serial

            num *= rack

            num = num % 1000 / 100
            val power = (num - 5).toInt()

            grid[x][y] = power
            pre[x][y] = power
        }
    }

    var maxPower = Int.MIN_VALUE
    var max = Triple(0, 0, 0)

    for (size in 1..grid.size) {
        for (x in 0 until grid.size - size) {
            (0 until grid[0].size - size)
                .toList()
                .parallelStream()
                .forEach { y ->
                    var triplePower = pre[x][y]

                    for (v in 0 until size - 1) {
                        triplePower += grid[x + size - 1][y + v]
                        triplePower += grid[x + v][y + size - 1]
                    }

                    triplePower += grid[x + size - 1][y + size - 1]
                    pre[x][y] = triplePower

                    if (triplePower > maxPower) {
                        maxPower = triplePower
                        max = Triple(x, y, size)
                    }
                }
        }
    }

    println(max)

    /*var biggest: Cell? = null
    var biggestSum: Int = Int.MIN_VALUE
    var biggestSize: Int? = null

    for (size in 1..300) {
        val old = System.currentTimeMillis()

        for (y in 0 until 300 - size) {
            for (x in 0 until 300 - size) {
                var sum = 0

                for (i in 0 until size) {
                    for (j in 0 until size) {
                        val cell = grid[y + i][x + j]

                        sum += cell.score
                    }
                }

                if (sum > biggestSum) {
                    biggestSum = sum
                    biggest = grid[y][x]
                    biggestSize = size
                }
            }
        }

        println("Run $size took ${System.currentTimeMillis() - old}ms")
    }*/

//    println("TOP RIGHT OF BIGGEST: $biggest, $biggestSize")
}

data class Cell(val pos: XY) {
    val score by lazy {

    }

    override fun toString(): String {
        return "(${pos.x},${pos.y})"
    }
}
