package me.falsehonesty.day14

import me.falsehonesty.utils.resource

fun main() {
    val lines = resource("/day14in.txt")

    val recipes = mutableListOf(3, 7)

    val elves = listOf(Elf(recipes, 0), Elf(recipes, 1))
    val target = 640441.toString()
//    val target = 640441
//    val recipeTarget = 10

//    while (recipes.size < target + recipeTarget) {

    var appendTime = 0

    while (target !in recipes.takeLast(10).joinToString(separator = "")) {
        val sum = elves.sumBy { recipes[it.index].toString().toInt() }

        recipes.addAll(sum.toString().toCharArray().map(Character::getNumericValue))

        elves.forEach {
            it.index = (it.index + recipes[it.index].toString().toInt() + 1) % recipes.size
        }
        /*val sum = elves.sumBy { recipes[it.index] }

        val nums = sum.toString().split("").filter { it.isNotBlank() }.map { it.toInt() }
        recipes.addAll(nums)

        elves.forEach(Elf::move)*/
    }

    println(recipes.joinToString(separator = "").indexOf(target))
//    val score = recipes.drop(target).take(recipeTarget).joinToString(separator = "")
//    println(score)
}

data class Elf(val recipes: MutableList<Int>, var index: Int) {
    fun move() {
        index = (index + recipes[index] + 1) % recipes.size
    }
}
