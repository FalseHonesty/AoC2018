package me.falsehonesty.day8

import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day8in.txt")

    val items = lines[0].split(" ").map { it.toInt() }
    val ite = items.iterator()

    val firstHeader = Header(ite.next(), ite.next())

    val parentNode = parseNode(firstHeader, ite)

    println(parentNode)
    println(sumNode(parentNode))
}

fun parseNode(header: Header, ite: Iterator<Int>): Node {
    val children = mutableListOf<Node>()

    for (x in 0 until header.childrenCount) {
        val subHeader = Header(ite.next(), ite.next())

        val subNode = parseNode(subHeader, ite)

        children.add(subNode)
    }

    val meta = mutableListOf<Int>()

    for (x in 0 until header.metaCount) {
        meta.add(ite.next())
    }

    return Node(header, children, meta)
}

fun sumNode(node: Node): Int {
    return if (node.children.isEmpty()) {
        node.meta.sum()
    } else {
        val indices = node.meta

        val importantChildren = indices
            .map { it - 1 }
            .filter { it >= 0 && it < node.children.size }
            .map { node.children[it] }

        importantChildren.sumBy { sumNode(it) }
    }

    /*p1
    var sum = node.meta.sum()

    node.children.forEach {
        sum += sumNode(it)
    }

    return sum*/
}

data class Node(val header: Header, val children: List<Node>, val meta: List<Int>)

data class Header(val childrenCount: Int, val metaCount: Int)
