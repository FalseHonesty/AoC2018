package me.falsehonesty.day3

import me.falsehonesty.utils.Coord
import me.falsehonesty.utils.resource
import me.falsehonesty.utils.splitWS

fun main() {
    val lines = resource("/day3in.txt")

    val items = lines.map {
        val split = it.splitWS()

        val id = split[0].replace("#", "").toInt()

        val (x, y) = split[2].replace(":", "").split(",").map { it.toInt() }

        val (w, h) = split[3].split("x").map { it.toInt() }

        Claim(id, y, x, w, h)
    }

    val claims = mutableMapOf<Coord, MutableList<Claim>>()
    val claimToCoords = mutableMapOf<Claim, MutableList<Coord>>()

    items.forEach {
        val coords = claimToCoords.getOrPut(it, { mutableListOf() })

        for (i in it.leftOff until (it.leftOff + it.width)) {
            for (j in it.topOff until (it.topOff + it.height)) {
                val coord = Coord(i, j)

                claims.getOrPut(coord, { mutableListOf() }).add(it)
                coords.add(coord)
            }
        }
    }

    val singleClaims = claims.filter { it.value.size == 1 }.mapValues { it.value[0] }
    val checked = mutableListOf<Claim>()

    singleClaims.forEach { outer ->
        if (checked.contains(outer.value)) return@forEach

        val matching = singleClaims.filter { it.value == outer.value }

        if (matching.size == outer.value.width * outer.value.height) {
            println(outer)
        }

        checked.add(outer.value)
    }

    /*println(claims.filter { it.value.size == 1 }.filter { outer ->
        claims.any {
            it.value.size > 1 && it.value.contains(outer.value[0])
        }
    })*/
    //println(claims.filter { it.value.size > 1 }.size)
}

data class Claim(val id: Int, val topOff: Int, val leftOff: Int, val width: Int, val height: Int)
