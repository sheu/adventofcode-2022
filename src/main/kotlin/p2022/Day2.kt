package p2022

import java.nio.file.Files
import java.nio.file.Paths

val opponentCharToRPS = mapOf('A' to RPS.ROCK, 'B' to RPS.PAPER, 'C' to RPS.SCISSORS)
val myCharToRPS = mapOf('X' to RPS.ROCK, 'Y' to RPS.PAPER, 'Z' to RPS.SCISSORS)
val charToOutcome = mapOf('X' to Outcome.LOSE, 'Y' to Outcome.DRAW, 'Z' to Outcome.WIN)
val RPSToValue = mapOf(RPS.ROCK to 1, RPS.PAPER to 2, RPS.SCISSORS to 3)

class Day2 {
    fun part1(input: List<String>): Int {
        return input.asSequence().map { it.split(" ") }.map { it[0].toCharArray()[0] to it[1].toCharArray()[0] }
            .map { opponentCharToRPS[it.first]!! to myCharToRPS[it.second]!! }
            .map { calculateScoreForSecondPlay(it.first, it.second) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input.asSequence().map { it.split(" ") }
            .map { it[0].toCharArray()[0] to it[1].toCharArray()[0] }
            .map { opponentCharToRPS[it.first]!! to charToOutcome[it.second]!! }
            .map { it.first to calculateOutcome(it.first, it.second) }
            .map { calculateScoreForSecondPlay(it.first, it.second) }
            .sum()
    }
}

enum class RPS {
    ROCK,
    PAPER,
    SCISSORS
}

enum class Outcome {
    WIN,
    DRAW,
    LOSE
}

fun calculateScoreForSecondPlay(firstPlay: RPS, secondPlayer: RPS): Int {
    if (firstPlay == secondPlayer) {
        return 3 + RPSToValue[secondPlayer]!!
    } else if (firstPlay == RPS.PAPER && secondPlayer == RPS.SCISSORS) {
        return 6 + RPSToValue[secondPlayer]!!
    } else if (firstPlay == RPS.PAPER && secondPlayer == RPS.ROCK) {
        return RPSToValue[secondPlayer]!!
    } else if (firstPlay == RPS.SCISSORS && secondPlayer == RPS.PAPER) {
        return 0 + RPSToValue[secondPlayer]!!
    } else if (firstPlay == RPS.SCISSORS && secondPlayer == RPS.ROCK) {
        return 6 + RPSToValue[secondPlayer]!!
    } else if (firstPlay == RPS.ROCK && secondPlayer == RPS.PAPER) {
        return 6 + RPSToValue[secondPlayer]!!
    } else if (firstPlay == RPS.ROCK && secondPlayer == RPS.SCISSORS) {
        return 0 + RPSToValue[secondPlayer]!!
    }
    error("Should never happen")

}

fun calculateOutcome(firstPlay: RPS, outcome: Outcome): RPS {
    return when (outcome) {
        Outcome.DRAW -> {
            firstPlay
        }
        Outcome.WIN -> {
            when (firstPlay) {
                RPS.SCISSORS -> RPS.ROCK
                RPS.ROCK -> RPS.PAPER
                RPS.PAPER -> RPS.SCISSORS
            }
        }
        else -> {
            when (firstPlay) {
                RPS.SCISSORS -> RPS.PAPER
                RPS.ROCK -> RPS.SCISSORS
                RPS.PAPER -> RPS.ROCK
            }
        }
    }
}

fun main() {
    println(
        Day2().part2(
            listOf(
                "A Y",
                "B X",
                "C Z"
            )
        )
    )
    val input = Files.readAllLines(Paths.get("src/main/resources/2022/day2.txt"))
    println(Day2().part1(input))
    println(Day2().part2(input))

}

