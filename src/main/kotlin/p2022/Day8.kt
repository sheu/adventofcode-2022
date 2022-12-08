package p2022

import java.nio.file.Files
import java.nio.file.Paths

class Day8 {
    fun part1(grid: List<List<Int>>): Int {
        var visibleCount = 0
        for (row in 0..grid.lastIndex) {
            for (col in 0..grid[row].lastIndex) {
                if (grid.isVisible(row, col)) {
                    visibleCount++
                }
            }
        }

        return visibleCount
    }

    fun part2(grid: List<List<Int>>): Int {

        val viewIndices = mutableListOf<Int>()
        for (row in 0..grid.lastIndex) {
            for (col in 0..grid[row].lastIndex) {
                val i = grid.viewIndex(row, col)
                viewIndices.add(i)
               // println("${grid[row][col]} ->  $i")
                //println(grid.views(row, col))
                // grid.views(row, col)
            }
        }

        return viewIndices.max()

    }
}

fun List<List<Int>>.pointToColumListDown(row: Int, col: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (currentRow in row + 1..lastIndex) {
        result.add(this[currentRow][col])
    }
    return result
}

fun List<List<Int>>.pointToColumListUp(row: Int, col: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (currentRow in row - 1 downTo 0) {
        result.add(this[currentRow][col])
    }
    return result
}

fun List<List<Int>>.pointToRowListRight(row: Int, col: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (currenCol in col + 1..this[row].lastIndex) {
        result.add(this[row][currenCol])
    }
    return result
}

fun List<List<Int>>.pointToRowListLeft(row: Int, col: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (currenCol in col - 1 downTo 0) {
        result.add(this[row][currenCol])
    }
    return result
}

fun List<List<Int>>.isVisible(row: Int, col: Int): Boolean {
    return if (row == 0 || col == 0 || row == this.lastIndex || col == this[row].lastIndex) {
        true
    } else {
        pointToColumListUp(row, col).max() < this[row][col] ||
                pointToColumListDown(row, col).max() < this[row][col] ||
                pointToRowListLeft(row, col).max() < this[row][col] ||
                pointToRowListRight(row, col).max() < this[row][col]

    }
}

fun List<List<Int>>.viewIndex(row: Int, col: Int): Int {
    if (row == 0 || col == 0 || row == this.lastIndex || col == this[row].lastIndex) {
       return 0
    }
    val currentValue = this[row][col]
    val i1 = if(pointToColumListUp(row, col).indexOfFirst { it >= currentValue } < 0)
        pointToColumListUp(row, col).size else 1 + pointToColumListUp(row, col).indexOfFirst { it >= currentValue }
    val i2 = if(pointToColumListDown(row, col).indexOfFirst { it >= currentValue } < 0)
        pointToColumListDown(row, col).size else 1 + pointToColumListDown(row, col).indexOfFirst { it >= currentValue }
    val i3 =  if(pointToRowListLeft(row, col).indexOfFirst { it >= currentValue } < 0)
        pointToRowListLeft(row, col).size else 1 + pointToRowListLeft(row, col).indexOfFirst { it >= currentValue }
    val i4 =if(pointToRowListRight(row, col).indexOfFirst { it >= currentValue } < 0)
        pointToRowListRight(row, col).size else  1 + pointToRowListRight(row, col).indexOfFirst { it >= currentValue }
    val directionalScores =  listOf(i1 , i2 , i3 , i4)

    return directionalScores.reduce {a,b -> a * b}

}
fun List<List<Int>>.views(row: Int, col: Int): List<List<Int>> {
    val currentValue = this[row][col]
    val directionalScores = listOf(pointToColumListUp(row, col),
    pointToColumListDown(row, col),
    pointToRowListLeft(row, col),
   pointToRowListRight(row, col))
    println("[$row][$col] -> $directionalScores (${this[row][col]})" )
    return directionalScores

}


fun main() {
    val testInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent().split("\n")
        .map { it.toCharArray().toList().map { char -> char.digitToInt() } }
    println(testInput)

    println(Day8().part1(testInput))
    println(Day8().part2(testInput))

    val finalInput = Files.readAllLines(Paths.get("src/main/resources/2022/day8.txt"))
        .map { it.toCharArray().toList().map { char -> char.digitToInt() } }
    println(Day8().part1(finalInput))
    println(Day8().part2(finalInput))
}

