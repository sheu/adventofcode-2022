package p2022

import java.nio.file.Files
import java.nio.file.Paths

val charToCode = ('a'..'z').foldIndexed(mapOf<Char, Int>())
{ index, acc, i -> acc + (i to index + 1) } + ('A'..'Z')
    .foldIndexed(mapOf<Char, Int>()) { index, acc, i ->
        acc + (i to index + 27)
    }

class Day3 {
    fun part1(input: List<String>): Int {
        println(charToCode)
        return input.asSequence().map { it.splitInTwo() }
            .map { charToCode[it.findCommonCharacter()]!! }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).map { charToCode[it.findCommonCharacter()]!! }.sum()
    }
}

fun String.splitInTwo() = this.substring(0, length / 2) to this.substring(length / 2, length)
fun Pair<String, String>.findCommonCharacter() = this.first.toSet().intersect(this.second.toSet()).toList().first()
fun List<String>.findCommonCharacter() = this[0].toSet().intersect(this[1].toSet()).intersect(this[2].toSet())
    .toList().first()

fun main() {
    val input = listOf(
        "vJrwpWtwJgWrhcsFMMfFFhFp",
        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
        "PmmdzqPrVvPwwTWBwg",
        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
        "ttgJtRGJQctTZtZT",
        "CrZsJsPPZsGzwwsLwLmpwMDw"
    )
    println(Day3().part1(input))
    println(Day3().part2(input))

    val input1 = Files.readAllLines(Paths.get("src/main/resources/2022/day3.txt"))
    println(Day3().part1(input1))
    println(Day3().part2(input1))
}