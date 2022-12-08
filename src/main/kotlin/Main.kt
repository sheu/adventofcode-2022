import p2022.Day1
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    val calories = Files.readAllLines(Paths.get("src/main/resources/2022/day1.txt"))
    val temp = mutableListOf<Long>()
    val elfList = mutableListOf<List<Long>>()
    calories.forEach {
        if (it.trim().isEmpty()) {
            elfList.add(temp.toList())
            temp.clear()
        } else {
            temp.add(it.toLong())
        }
    }
    println(Day1().part1(elfList))
    println(Day1().part2(elfList))


}

fun getLinesFromFiles(path: String) =
    Files.readAllLines(Paths.get(path))


