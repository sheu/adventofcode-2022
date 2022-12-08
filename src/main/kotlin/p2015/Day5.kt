package p2015

import getLinesFromFiles

class Day5 {
    fun part1(): Int {
        val line = getLinesFromFiles("src/main/resources/2015/day5.txt")
        val result = line.count {
            it.containsAtleastNVowels(3)
                    && it.containsAtleastNConsecutiveLetters(1)
                    && it.doesNotContainAnySubstring(listOf("ab", "cd", "pq", "xy"))
        }
        return result
    }

}

fun String.containsAtleastNVowels(n: Int) =
    this.count { it in listOf('a', 'e', 'i', 'o', 'u') } >= n

fun String.containsAtleastNConsecutiveLetters(n: Int) =
    this.zipWithNext().count { (a, b) -> a == b } >= n

fun String.doesNotContainAnySubstring(substrings: List<String>) =
    substrings.count { this.contains(it) } == 0

