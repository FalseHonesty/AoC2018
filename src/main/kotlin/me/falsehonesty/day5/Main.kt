package me.falsehonesty.day5

import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day5in.txt")

    val items = lines[0].toCharArray().toMutableList()

    var smallest = Int.MAX_VALUE

    for (letter in 'a'..'z') {
        val copy = items.toMutableList()

        react(copy, letter)
        react(copy)

        if (copy.size < smallest) smallest = copy.size
    }

    //println(items.joinToString(separator = ""))
    //println(items.size)
    println(smallest)
}

fun react(list: MutableList<Char>, onlyLetter: Char? = null) {
    if (onlyLetter != null) {
        list.removeAll { it.toLowerCase() == onlyLetter }
    }

    while (true) {
        var foundAny = false

        for (i in 0 until list.size) {
            if (i >= list.size - 1) continue

            if (list[i].toLowerCase() == list[i + 1].toLowerCase() && (list[i] != list[i + 1])) {
                list.removeAt(i)
                list.removeAt(i)
                foundAny = true
            }
        }

        if (!foundAny) break
    }
}
