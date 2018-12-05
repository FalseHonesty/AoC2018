package me.falsehonesty.utils

class Grid<T>(private val size: Int) {
    private var cursor = Coord(0, 0)
    private var content = listOf2D<Node<T>>(size, Node())

    fun getAtCursor(): T? {
        return content[cursor.first, cursor.second].value
    }

    operator fun get(i: Int, j: Int): T? {
        return content[i][j].value
    }

    fun moveCursor(dir: Direction, moveToBlanks: Boolean = false) {
        val oldCursor = cursor
        val newCursor = Coord(cursor.first + dir.dx, cursor.second + dir.dy)

        if (newCursor.exceeds(size - 1)) {
            return
        }

        if (moveToBlanks || getAtCursor() != null) {
            setCursor(newCursor)
        }
    }

    fun setContent(content: MutableList2D<Node<T>>) {
        this.content = content
    }

    fun setCursor(coord: Coord) = this.apply {
        cursor = coord
    }

    fun setCursor(x: Int, y: Int) = this.setCursor(Coord(x, y))

    class Node<T>(val value: T? = null)

    enum class Direction(val dx: Int, val dy: Int) {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0),

        UP_RIGHT(1, -1),
        DOWN_RIGHT(1, 1),
        DOWN_LEFT(-1, 1),
        UP_LEFT(-1, -1)
    }
}
