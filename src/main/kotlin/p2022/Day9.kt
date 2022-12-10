package p2022

import geometry.PixelGameEngine
import java.awt.Color
import java.nio.file.Files
import java.nio.file.Paths

class Day9 {
    fun part1(input: List<String>): Int {
       val points = input.asSequence().map { it.split(" ") }
            .map { (a, b) -> a.single() to b.trim().toInt() }
            .map { it.expand() }.flatMap { it }
            .fold(Triple(0 to 0, 0 to 0, setOf(0 to 0))) { acc, i ->
                acc.move(i.first)
            }.third


        return points.size
    }

    fun part2(input: List<String>): Int {
        val rope = buildList<Pair<Int, Int>> {
            for(i in 1..10) {
                add(0  to 0)
            }
        }

        val points = input.asSequence().map { it.split(" ") }
            .map { (a, b) -> a.single() to b.trim().toInt() }
            .map { it.expand() }.flatMap { it }
            .fold(Pair(rope, setOf(0 to 0))) { acc, i ->
                acc.first.move(i.first) to acc.second+acc.first.last()
            }.second

        return points.size
    }

    fun Pair<Char, Int>.expand(): List<Pair<Char, Int>> {
        val pair = this
        return buildList {
            for (i in 1..pair.second) {
                add(pair.first to 1)
            }
        }
    }
}
fun Pair<Int, Int>.move(dir: Char): Pair<Int, Int> {
    return when(dir) {
        'R' -> first + 1 to second
        'U' -> first to second + 1
        'L' -> first - 1 to second
        'D' -> first to second - 1
        else -> {error ("Unknown direction")}
    }
}

fun List<Pair<Int, Int>>.move(dir: Char): List<Pair<Int, Int>> {
    return drop(1).fold(listOf(first().move(dir))) {
        acc, i -> acc + acc.last().moveTail(i)
    }
}

fun Pair<Int, Int>.moveTail(tail: Pair<Int, Int>) : Pair<Int, Int> {
    if(touching(this, tail)) {
        return tail
    } else if (first == tail.first || second == tail.second) {
        if (second > tail.second && second - tail.second > 1) {
            return tail.first to  tail.second + 1
        } else if ( second <  tail.second &&  tail.second -  second > 1) {
            return tail.first to  tail.second - 1
        } else if ( first >  tail.first) {
            return tail.first + 1 to  tail.second
        } else if ( first <  tail.first) {
            return tail.first - 1 to  tail.second
        }
    } else { // diagonal moves
        if ( first >  tail.first &&  second >  tail.second) {
            return tail.first + 1 to  tail.second + 1
        } else if ( first <  tail.first &&  second >  tail.second) {
            return tail.first - 1 to  tail.second + 1
        } else if ( first <  tail.first &&  second <  tail.second) {
            return tail.first - 1 to  tail.second - 1
        } else if ( first >  tail.first &&  second <  tail.second) {
            return tail.first + 1 to  tail.second - 1
        }
    }
   error("Should not happen")
}
fun Triple<Pair<Int, Int>, Pair<Int, Int>, Set<Pair<Int, Int>>>.move(dir: Char): Triple<Pair<Int, Int>, Pair<Int, Int>, Set<Pair<Int, Int>>> {
    var h = this.first
    var t = this.second
    var prevTailsPosition = this.third
    when (dir) {
        'R' -> {
            h = h.first + 1 to h.second
        }
        'U' -> {
            h = h.first to h.second + 1
        }
        'L' -> {
            h = h.first - 1 to h.second
        }
        'D' -> {
            h = h.first to h.second - 1
        }
    }

    if (touching(h, t)) {
        return Triple(h, t, prevTailsPosition)
    } else if (h.first == t.first || h.second == t.second) {
        if (h.second > t.second && h.second - t.second > 1) {
            t = t.first to t.second + 1
        } else if (h.second < t.second && t.second - h.second > 1) {
            t = t.first to t.second - 1
        } else if (h.first > t.first) {
            t = t.first + 1 to t.second
        } else if (h.first < t.first) {
            t = t.first - 1 to t.second
        }
    } else { // diagonal moves
        if (h.first > t.first && h.second > t.second) {
            t = t.first + 1 to t.second + 1
        } else if (h.first < t.first && h.second > t.second) {
            t = t.first - 1 to t.second + 1
        } else if (h.first < t.first && h.second < t.second) {
            t = t.first - 1 to t.second - 1
        } else if (h.first > t.first && h.second < t.second) {
            t = t.first + 1 to t.second - 1
        }

    }
    return Triple(h, t, prevTailsPosition + t)

}

fun touching(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
    return kotlin.math.abs(head.first - tail.first) <= 1 && kotlin.math.abs(head.second - tail.second) <= 1
}

fun main() {
    val testInput  = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
    """.trimIndent().split("\n")
    println(Day9().part1(testInput))
    println(Day9().part2(testInput))
    val finalInput = Files.readAllLines(Paths.get("src/main/resources/2022/day9.txt"))
    println(Day9().part1(finalInput))
    println(Day9().part2(finalInput))

}

class Day9Vis(private val width: Int, private val height: Int, private val data: Set<Pair<Int, Int>>): PixelGameEngine() {

    init {
        construct(width, height, 5, appName = "Test")
        limitFps = 25
    }

    override fun onUpdate(elapsedTime: Long, frame: Long) {
        for (p in data) {
            draw(p.first, p.second, Color.RED)
        }
    }

}