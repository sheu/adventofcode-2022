package p2022

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayDeque

val stackIndex = mapOf<Int, Int>(1 to 0, 5 to 1, 9 to 2, 13 to 3, 17 to 4, 21 to 5, 25 to 6, 29 to 7, 33 to 8)

class Day5 {

    fun part1(input: List<kotlin.collections.ArrayDeque<Char>>, moves: List<Triple<Int, Int, Int>>): String {
        moves.forEach { (a, b, c) ->
            input.move(a, b, c)
        }
        return input.filter { it.isNotEmpty() }.map { it.first() }.joinToString("")
    }

    fun part2(input: List<kotlin.collections.ArrayDeque<Char>>, moves: List<Triple<Int, Int, Int>>): String {
        moves.forEach { (a, b, c) ->
            input.move9001(a, b, c)
        }
        return input.filter { it.isNotEmpty() }.map { it.first() }.joinToString("")
    }

}

fun main() {
    val inputText = Files.readAllLines(Paths.get("src/main/resources/2022/day5.txt"))
    val input = inputText.parseStack(8)
    val inputMoves = inputText.parseMoves(10)
    println(Day5().part1(input, inputMoves))
    println(Day5().part2(inputText.parseStack(8), inputMoves))
}

fun List<String>.parseStack(lineWithStackNumbers: Int): List<kotlin.collections.ArrayDeque<Char>> {
    val stacks = subList(0, lineWithStackNumbers).map { it.toCharArray().toList() }.toStacks()
    println(stacks.first().first())
    return stacks
}

fun List<String>.parseMoves(instructionsIndex: Int): List<Triple<Int, Int, Int>> {
    val result = subList(instructionsIndex, lastIndex + 1).map {
        it.replace("move", "").replace("from", "").replace("to", "").trim()
    }
        .map { it.split(Regex("\\s+")) }
    println(result)
    return result
        .map { (a, b, c) -> Triple(a.toInt(), b.toInt(), c.toInt()) }
}

fun List<List<Char>>.toStacks(): List<kotlin.collections.ArrayDeque<Char>> {
    println(this)
    val result = mutableListOf<Queue<Char>>()
    for (i in 0..this.last().lastIndex) {
        result.add(LinkedList<Char>())
        println(result)
    }

    println(result)
    println("=================")
    this.forEach { sl ->
        println(sl)
        sl.forEachIndexed { i, s ->


            println("$i -> $s")
            if (s in 'A'..'Z') result[stackIndex[i]!!].add(s)
            println(result[i])

        }
    }

    return result.map { ArrayDeque(it) }
}

fun List<kotlin.collections.ArrayDeque<Char>>.move(num: Int, from: Int, to: Int) {
    repeat(num) {
        if (this[to - 1].isEmpty()) {
            this[to - 1].add(this[from - 1].removeFirst())
        } else {
            this[to - 1].addFirst(this[from - 1].removeFirst())
        }
    }
}

fun List<kotlin.collections.ArrayDeque<Char>>.move9001(num: Int, from: Int, to: Int) {
    val stack = this[from - 1].removeFirstN(num)
    if (this[to - 1].isEmpty()) {
        this[to - 1].addAll(stack)
    } else {
        while (stack.isNotEmpty()) {
            this[to - 1].addFirst(stack.pop())
        }
    }
    println("===================\nList of stacks: $this")

}

fun kotlin.collections.ArrayDeque<Char>.removeFirstN(n: Int): Stack<Char> {
    println(this)
    val stack = Stack<Char>()
    repeat(n) {
        stack.push(removeFirst())
    }
    println(this)

    return stack
}


