package p2015

import geometry.Point
import java.nio.file.Files
import java.nio.file.Paths

class Day3 {
    fun part1(): Int {
        return Files.readAllLines(Paths.get("src/main/resources/2015/day3.txt")).first()
            .fold(listOf(Point(0, 0))) { i, acc -> i + i.last().move(acc) }
            .distinct().count()


    }

    fun part2(): Int {
        val pairs = Files.readAllLines(Paths.get("src/main/resources/2015/day3.txt")).first()
            .foldIndexed(Pair(listOf(Point(0, 0)), listOf(Point(0, 0))))
            { index, acc, v ->
                if (index % 2 == 0) Pair(acc.first + acc.first.last().move(v), acc.second)
                else Pair(acc.first, acc.second + acc.second.last().move(v))
            }
        return (pairs.first + pairs.second).distinct().count()


    }
}
