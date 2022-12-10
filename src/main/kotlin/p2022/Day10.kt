package p2022

import java.nio.file.Files
import java.nio.file.Paths

class Day10 {

    fun part1(input: List<String>): Int {
        val result = input.fold(listOf(1)) { acc, i -> acc.applyInstruction(i)}

        return listOf( result[19] * 20 , result[59]* 60,  result[99] *100,  result[139] *140,  result[179] *180,  result[219] * 220).sum()
    }
    fun part2(input: List<String>): String {
        return input.fold(listOf(1)) { acc, i -> acc.applyInstruction(i) }
            .mapIndexed { index, i -> if (index % 40 in i - 1..i + 1) "#" else "." }
            .chunked(40).joinToString("\n") { it.joinToString("") }
    }
}

fun List<Int>.applyInstruction(instruction: String) : List<Int> {
    return if(instruction == "noop") {
        this + last()
    }else  {
        val num = instruction.split(" ")[1].toInt()
        this + last()+(last() + num)
    }
}

fun main() {
    val inputTest  = """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent().split("\n")
    Day10().part1(inputTest)
    Day10().part2(inputTest)

    val finalInput = Files.readAllLines(Paths.get("src/main/resources/2022/day10.txt"))

    println(Day10().part1(finalInput))
    println(Day10().part2(finalInput))


}