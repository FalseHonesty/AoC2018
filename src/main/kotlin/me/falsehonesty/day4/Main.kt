package me.falsehonesty.day4

import me.falsehonesty.utils.eachCount
import me.falsehonesty.utils.resource
import java.text.SimpleDateFormat

fun main() {
    var lines = resource("/day4in.txt")

    val guards = mutableListOf<Guard>()
    var currentGuard: Guard? = null
    var currentSleep: AOCDate? = null

    val dateFormat = SimpleDateFormat("[yyyy-MM-dd hh:mm]")

    lines = lines.sortedBy {
        val split = it.split(" ")
        dateFormat.parse(split[0] + " " + split[1])
    }

    val items = lines.forEach {
        val split = it.split(" ")

        val (_, month, day) = split[0].replace("[", "").split("-").map { it.toInt() }
        val minutes = split[1].replace("]", "").split(":")[1].toInt()

        when {
            split[2] == "Guard" -> {
                if (currentGuard != null && !guards.contains(currentGuard!!)) guards.add(currentGuard!!)
                currentGuard = if (guards.any { it.id == split[3].replace("#", "").toInt() }) {
                    guards.first { it.id == split[3].replace("#", "").toInt() }
                } else {
                    Guard(mutableListOf(), split[3].replace("#", "").toInt())
                }
            }
            split[2] == "falls" -> currentSleep = AOCDate(month, day, minutes, 0)
            split[2] == "wakes" -> {
                currentSleep?.copy(minuteEnd = minutes - 1)?.let { it1 -> currentGuard?.sleepTimes?.add(it1) }
                currentSleep = null
            }
        }
    }

    val sleepiest = guards.maxBy {
        it.sleepTimes.sumBy {
            it.minuteEnd - it.minuteStart
        }
    }

    val flat = sleepiest!!.sleepTimes.map { (it.minuteStart..it.minuteEnd).toList() }.flatten().eachCount()
    val sleepiestTime = flat.maxBy { it.value }
    println(sleepiestTime!!.key * sleepiest.id)

    val sleepiest2 = guards.maxBy {
        it.sleepTimes.map { (it.minuteStart..it.minuteEnd).toList() }.flatten().eachCount().maxBy { it.value }?.value ?: -1
    }

    println(sleepiest2!!.id * sleepiest2.sleepTimes.map { (it.minuteStart..it.minuteEnd).toList() }.flatten().eachCount().maxBy { it.value }!!.key)
}

data class Guard(val sleepTimes: MutableList<AOCDate>, val id: Int)

data class AOCDate(val month: Int, val day: Int, val minuteStart: Int, val minuteEnd: Int)
