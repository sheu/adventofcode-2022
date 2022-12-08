package p2022

class Day1 {
    fun part1(caloriesPerElf: List<List<Long>>): Long {
        return caloriesPerElf.maxOfOrNull { it.sum() }!!
    }

    fun part2(caloriesPerElf: List<List<Long>>): Long {
        println(caloriesPerElf)
        val sublist = caloriesPerElf.map { it.sum() }.sortedDescending().subList(0, 3)
        println(sublist)
        return sublist.sum()

    }
}