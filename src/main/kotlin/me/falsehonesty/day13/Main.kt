package me.falsehonesty.day13

import me.falsehonesty.utils.Direction
import me.falsehonesty.utils.XY
import me.falsehonesty.utils.resource
import me.falsehonesty.utils.xy

fun main() {
    val lines = resource("/day13in.txt")

    val map = lines.map { it.toCharArray() }

    val carts = mutableListOf<Cart>()

    map.forEachIndexed { y, chars ->
        chars.forEachIndexed { x, char ->
            val cart = when (char) {
                '>' -> Cart(x xy y, Direction.RIGHT, Move.LEFT)
                'v' -> Cart(x xy y, Direction.DOWN, Move.LEFT)
                '<' -> Cart(x xy y, Direction.LEFT, Move.LEFT)
                '^' -> Cart(x xy y, Direction.UP, Move.LEFT)
                else -> null
            }

            cart?.let { carts.add(it) }
        }
    }

    while (carts.size > 1) {
        val crashed = mutableListOf<Cart>()

        carts.forEach {
            val newPos = it.position.off(it.direction)

            val crash = carts.find { it.position == newPos }

            if (crash != null) {
                println("CRASH AT $newPos")

                crashed.add(crash)
                crashed.add(it)

                return@forEach
            }

            it.position = newPos

            when (map[newPos.y][newPos.x]) {
                '+' -> {
                    val turn = it.nextMove.apply(it.direction)
                    it.direction = turn
                    it.nextMove = it.nextMove.next()
                }
                '\\' -> {
                    if (it.direction == Direction.LEFT || it.direction == Direction.RIGHT) {
                        it.direction = it.direction.turnRight()
                    } else {
                        it.direction = it.direction.turnLeft()
                    }
                }
                '/' -> {
                    if (it.direction == Direction.LEFT || it.direction == Direction.RIGHT) {
                        it.direction = it.direction.turnLeft()
                    } else {
                        it.direction = it.direction.turnRight()
                    }
                }
            }
        }

        carts.removeAll(crashed)
    }

    println(carts.first())
}

data class Cart(var position: XY, var direction: Direction, var nextMove: Move)

enum class Move(private val func: (Direction) -> Direction) {
    LEFT(Direction::turnLeft),
    STRAIGHT({ it }),
    RIGHT(Direction::turnRight);

    fun apply(dir: Direction): Direction {
        return func(dir)
    }

    fun next(): Move {
        return when(this) {
            LEFT -> STRAIGHT
            STRAIGHT -> RIGHT
            RIGHT -> LEFT
        }
    }
}
