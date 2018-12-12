package me.falsehonesty.day7

import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day7in.txt")

    val steps = ('A'..'Z').associateWith {
        Step(it)
    }

    lines.forEach { line ->
        val (necessary, id) = line.split(" ").filter { it.length == 1 }.map { it.toCharArray()[0] }
        val related = steps[id]!!

        related.necessary.add(steps[necessary]!!)
    }

    val sb = StringBuilder()
    val stepsCopy = steps.toMutableMap()
    var seconds = 0
    val jobs = mutableListOf<Job>()

    while (stepsCopy.isNotEmpty() || jobs.isNotEmpty()) {
        val active = jobs.filter { it.requireTime <= seconds }

        active.forEach { job ->
            sb.append(job.step.key)
            stepsCopy.forEach { it.value.necessary.remove(job.step.value) }
        }

        jobs.removeAll(active)

        val iterator = stepsCopy.filter { it.value.necessary.size == 0 }.iterator()
        while (iterator.hasNext() && jobs.size < 5) {
            val ready = iterator.next()

            jobs.add(Job(seconds + 60 + (ready.key - 64).toInt(), ready))

            stepsCopy.remove(ready.key)
        }

        seconds++
    }

    println(sb)
    println(seconds - 1)
}

data class Step(val id: Char, val necessary: MutableList<Step> = mutableListOf())
data class Job(val requireTime: Int, val step: Map.Entry<Char, Step>)
