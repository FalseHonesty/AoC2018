package me.falsehonesty.day16

import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day16in.txt")

    val regex = """\w+:\s+\[(.*)]""".toRegex()
    val cases = mutableListOf<Case>()

    for (i in 0 until 3160 step 4) {
        val before = regex.matchEntire(lines[i])!!.groupValues[1].split(", ").map { it.toInt() }

        val (opcode, a, b, c) = lines[i + 1].split(" ").map { it.toInt() }

        val after = regex.matchEntire(lines[i + 2])!!.groupValues[1].split(", ").map { it.toInt() }

        cases.add(Case(
            before, after, opcode, Triple(a, b, c)
        ))
    }

    println(cases.size)

    val opcodeMap = mutableMapOf<Int, MutableSet<Opcode>>()
    var threes = 0

    cases.forEach { case ->
        val possible = opcodeMap.getOrPut(case.opcode) { Opcode.values().toMutableSet() }
        var matched = 0

        for (op in Opcode.values()) {
            val registers = case.before.toMutableList()

            op.action(registers, case.parameters)

            if (registers != case.after) {
                possible.remove(op)
            } else {
                matched++
            }
        }

        if (matched >= 3) {
            threes++
        }
    }

    println(opcodeMap)
    println("$threes threes")

    val realMap = mutableMapOf<Int, Opcode>()

    while (realMap.size < 16) {
        val found = opcodeMap.filter { it.value.size == 1 }.entries.first()

        val code = found.value.first()

        realMap[found.key] = code

        opcodeMap.forEach { it.value.remove(code) }
    }

    println(realMap.toSortedMap())

    val registers = mutableListOf(0, 0, 0, 0)

    for (i in 3160 until lines.size) {
        val (opcode, a, b, c) = lines[i].split(" ").map { it.toInt() }

        realMap[opcode]!!.action(registers, Triple(a, b, c))
    }

    println(registers)
}

enum class Opcode(val action: (MutableList<Int>, Triple<Int, Int, Int>) -> Unit) {
    ADDR({ r, (a, b, c) -> r[c] = r[a] + r[b] }),
    ADDI({ r, (a, b, c) -> r[c] = r[a] + b }),

    MULR({ r, (a, b, c) -> r[c] = r[a] * r[b] }),
    MULI({ r, (a, b, c) -> r[c] = r[a] * b }),

    BANR({ r, (a, b, c) -> r[c] = r[a] and r[b] }),
    BANI({ r, (a, b, c) -> r[c] = r[a] and b }),

    BORR({ r, (a, b, c) -> r[c] = r[a] or r[b] }),
    BORI({ r, (a, b, c) -> r[c] = r[a] or b }),

    SETR({ r, (a, _, c) -> r[c] = r[a] }),
    SETI({ r, (a, _, c) -> r[c] = a }),

    GTIR({ r, (a, b, c) -> r[c] = if (a > r[b]) 1 else 0 }),
    GTRI({ r, (a, b, c) -> r[c] = if (r[a] > b) 1 else 0 }),
    GTRR({ r, (a, b, c) -> r[c] = if (r[a] > r[b]) 1 else 0 }),

    EQIR({ r, (a, b, c) -> r[c] = if (a == r[b]) 1 else 0 }),
    EQRI({ r, (a, b, c) -> r[c] = if (r[a] == b) 1 else 0 }),
    EQRR({ r, (a, b, c) -> r[c] = if (r[a] == r[b]) 1 else 0 }),
}

data class Case(val before: List<Int>, val after: List<Int>, val opcode: Int, val parameters: Triple<Int, Int, Int>)
