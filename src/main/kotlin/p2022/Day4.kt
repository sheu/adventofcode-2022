package p2022

import java.nio.file.Files
import java.nio.file.Paths

class Day4 {
    fun part1(input: List<Pair<IntRange, IntRange>>): Int {
        val result = input.count { it.isOverlaping() }
        return result
    }

    fun part2(input: List<Pair<IntRange, IntRange>>): Int {
        return input.count { it.isKindOfOverlapping() }
    }
}

fun Pair<IntRange, IntRange>.isOverlaping(): Boolean {
    return (first.first in second && first.last in second)
            || (second.first in first && second.last in first)
}

fun Pair<IntRange, IntRange>.isKindOfOverlapping(): Boolean {
    return (first.first in second || first.last in second)
            || (second.first in first || second.last in first)
}

fun List<String>.parse(): List<Pair<IntRange, IntRange>> {
    val result = this.map { it.split(",") }
        .map { (a, b) -> a.split("-") to b.split("-") }
        .map { it.first.toIntRange() to it.second.toIntRange() }
    return result
}

fun List<String>.toIntRange() = IntRange(this[0].toInt(), this[1].toInt())

fun main() {
    val inputTest = Files.readAllLines(Paths.get("src/main/resources/2022/day4test.txt"))
    val input = inputTest.parse()
    println(Day4().part1(input))
    println(Day4().part2(input))

    val inputFinal = Files.readAllLines(Paths.get("src/main/resources/2022/day4.txt"))
    val inputParsed = inputFinal.parse()
    println(Day4().part1(inputParsed))
    println(Day4().part2(inputParsed))

}


