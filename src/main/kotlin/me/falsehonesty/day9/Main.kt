package me.falsehonesty.day9

import java.util.*

fun main() {
    val max = 71938 * 100
    val players = 462

    val circle = Deque()
    val scores = LongArray(players)
    circle.addFirst(0)

    for (x in 1..max) {
        if (x % 23 == 0) {
            circle.rotate(-7)
            scores[x % players] += x + circle.pop().toLong()
        } else {
            circle.rotate(2)
            circle.addLast(x)
        }
    }

    println(scores)
    println(scores.max())
}

class Deque : ArrayDeque<Int>() {
    fun rotate(places: Int) {
        if (places < 0) {
            repeat(-places - 1) {
                addLast(remove())
            }
        } else {
            repeat(places) {
                addFirst(removeLast())
            }
        }
    }
}
