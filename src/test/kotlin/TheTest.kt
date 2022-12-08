import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import p2016.Day1
import p2016.FacingDirection
import p2016.TurningDirection
import p2016.Instruction
import java.nio.file.Files
import java.nio.file.Paths


class TheTest {

    @Test
    fun dayPart1() {
       val day1 = Day1()
        day1.part1(listOf(Instruction(TurningDirection.RIGHT, 2), Instruction(TurningDirection.LEFT, 3))) shouldBe 5
        day1.part1(listOf(Instruction(TurningDirection.RIGHT, 1))) shouldBe 1

        day1.part1(listOf(Instruction(TurningDirection.RIGHT, 2),
            Instruction(TurningDirection.RIGHT, 2), Instruction(TurningDirection.RIGHT, 2))) shouldBe 2

        //R5, L5, R5, R3
        day1.part1(listOf(Instruction(TurningDirection.RIGHT, 5),
            Instruction(TurningDirection.LEFT, 5),
            Instruction(TurningDirection.RIGHT, 5),
            Instruction(TurningDirection.RIGHT, 3)
        )) shouldBe 12

    }

    @Test
    fun testInstructionExtraction() {
        Day1().parse("R2") shouldBe listOf(Instruction(TurningDirection.RIGHT, 2))
        Day1().parse("R2, L3") shouldBe listOf(Instruction(TurningDirection.RIGHT, 2), Instruction(TurningDirection.LEFT, 3))

    }



    @Test
    fun theFinalTest() {
        val day1 = Day1()
        val input = day1.parse(Files.readAllLines(Paths.get("src/main/resources/2016/day1.txt"))[0])
        day1.part1(input) shouldBe 278
        day1.part2(input) shouldBe 10
    }
}