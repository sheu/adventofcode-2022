package p2016

import java.lang.Math.abs


fun Pair<Point, Point>.toLinePoints(): List<Point> {
    return if (first.x == second.x) {
        if (first.y < second.y)
            generateSequence(first) { Point(it.x, it.y + 1).takeIf { c -> c.y < second.y } }.toList()
        else
            generateSequence(first) { Point(it.x, it.y - 1).takeIf { c -> c.y > second.y } }.toList()

    } else {
        if (first.x < second.x)
            generateSequence(first) { Point(it.x + 1, it.y).takeIf { c -> c.x < second.x } }.toList()
        else
            generateSequence(first) { Point(it.x - 1, it.y).takeIf { c -> c.x > second.x } }.toList()
    }
}

fun List<Point>.intersect(other: List<Point>) = any { it in other }
fun List<Point>.pointOfIntersection(other: List<Point>) = first { it in other }


class Day1 {
    fun part1(instructions: List<Instruction>): Int {
        val result = instructions.fold(FacingDirection.NORTH to Point(0, 0)) { acc, i ->
            i.move(acc.first, acc.second)
        }
        return Point(0, 0) distanceTo result.second
    }

    fun part2(instructions: List<Instruction>): Int {

        val distinct = mutableSetOf<Point>()
        val allPoints = instructions.asSequence().scan(FacingDirection.NORTH to Point(0, 0)) { acc, i ->
            i.move(acc.first, acc.second)
        }.map { it.second }
            .zipWithNext()
            .map { it.toLinePoints() }
            .flatMap { it.asSequence() }
            .forEach {
                if (!distinct.add(it)) {
                    println("This is it: $it")
                    val result = Point(0, 0) distanceTo it
                    println("Answer: $result")
                    return result
                }
            }



        println(allPoints)


        return Point(0, 0) distanceTo Point(0, 0)
    }

    fun parse(input: String): List<Instruction> {
        return input.split(",").map { create(it.trim()) }
    }

    companion object {
        fun create(input: String): Instruction {
            val dir = when (input[0]) {
                'R' -> TurningDirection.RIGHT
                'L' -> TurningDirection.LEFT
                else -> error("Unknown turn direction")
            }
            val len = input.substring(1).toInt()

            return Instruction(dir, len)
        }
    }
}

enum class TurningDirection {
    RIGHT,
    LEFT
}

enum class FacingDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST
}


data class Instruction(val dir: TurningDirection, val steps: Int) {
    private fun turn(currentFacingDirection: FacingDirection): FacingDirection {
        return when (dir) {
            TurningDirection.RIGHT -> FacingDirection.values()[(currentFacingDirection.ordinal + 1) % 4]
            TurningDirection.LEFT -> FacingDirection.values()[(currentFacingDirection.ordinal - 1 + 4) % 4]
        }
    }

    fun move(dir: FacingDirection, currPosition: Point): Pair<FacingDirection, Point> {
        return when (val newDirection = turn(dir)) {
            FacingDirection.NORTH -> newDirection to Point(currPosition.x, currPosition.y + steps)
            FacingDirection.EAST -> newDirection to Point(currPosition.x + steps, currPosition.y)
            FacingDirection.WEST -> newDirection to Point(currPosition.x - steps, currPosition.y)
            FacingDirection.SOUTH -> newDirection to Point(currPosition.x, currPosition.y - steps)
        }
    }
}


data class Point(val x: Int, val y: Int) {
    fun add(delta: Point) = Point(x + delta.x, y + delta.y)
    infix fun distanceTo(other: Point): Int {
        return abs(x - other.x) + abs(y - other.y)
    }
}

