package p2022

import java.nio.file.Files
import java.nio.file.Paths

class Day7 {

}

data class AocFile(val name: String, val size: Int = 0, val isDirectory: Boolean = true)
class TreeNode<T>(
    private val value: T,
    private val children: MutableList<TreeNode<T>> = mutableListOf(),
    private var parent: TreeNode<T>? = null
) {
    fun add(child: TreeNode<T>) {
        require(child.parent == null)
        child.parent = this
        children.add(child)
    }

    fun getValue() = value
    fun getChildren() = children.toList()
    fun addChildren(listOfChildren: List<TreeNode<T>>) {
        listOfChildren.forEach { add(it) }
    }

    fun countSize(sizes: MutableList<Pair<T, Long>>, counter: (T) -> Long, nameExtractor: (T) -> T): Long {
        val result = counter(this.getValue())
        var childrenResult = 0L
        for (e in children) {
            childrenResult += e.countSize(sizes, counter, nameExtractor)
        }
        sizes.add(nameExtractor(this.getValue()) to result + childrenResult)
        return result + childrenResult
    }

    fun getParent() = parent
    fun findOrNull(predicate: (T) -> Boolean): T? {
        if (predicate(this.getValue())) {
            return getValue()
        } else {
            val files = children.filter { it.children.isEmpty() }
            for (f in files) {
                if (predicate(f.value)) {
                    return f.value
                }
            }
            val dir = children.filter { it.children.isNotEmpty() }
            dir.forEach {
                val result = it.findOrNull(predicate)
                if (result != null) {
                    return result
                }
            }
        }
        return null
    }


}

fun parse(input: List<String>): TreeNode<AocFile> {
    val rootDirectory = AocFile("/")
    val root = TreeNode<AocFile>(rootDirectory, mutableListOf(), null)
    var currentNode = root
    input.forEach { inputString ->
        if (inputString.startsWith("dir")) {
            currentNode.add(TreeNode(AocFile(inputString.split(" ")[1]), mutableListOf()))
        } else if (inputString.startsWith("$ cd ")) {
            val newDirectory = inputString.split(" ")[2]
            currentNode = if (newDirectory == "..") {
                currentNode.getParent()!!
            } else {
                if (newDirectory == "/") {
                    root
                } else {
                    currentNode.getChildren().find { it.getValue().name == newDirectory }!!
                }
            }
        } else if (inputString.matches(Regex("^\\d+\\s.+"))) {
            val (size, name) = inputString.split(" ")
            currentNode.add(TreeNode(AocFile(name, size.toInt(), false), mutableListOf()))
        }
    }
    return root
}

fun main() {
    val testInput = """ ${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k""".split("\n")
    println(testInput)
//part1
    val testTree = parse(testInput)
    println(testTree)
    val testFileToSizePairs = mutableListOf<Pair<AocFile, Long>>()
    testTree.countSize(testFileToSizePairs, { it.size.toLong() }, { it })
    println(testFileToSizePairs)
    val testResult = testFileToSizePairs
        .map { (k, v) -> k to v }
        .filter { it.first.isDirectory && it.second <= 100000 }.sumOf { it.second }

    println(testResult)

    val finalTestData = Files.readAllLines(Paths.get("src/main/resources/2022/day7.txt"))
    val finalTree = parse(finalTestData)

    val finalFileToSize = mutableListOf<Pair<AocFile, Long>>()
    finalTree.countSize(finalFileToSize, { it.size.toLong() }, { it })

    val finalResult = finalFileToSize
        .asSequence()
        .filter { it.first.isDirectory }
        .map { it.first.name to it.second }
        .filter { it.second <= 100_000 }
        .sumOf { it.second }

    println(finalResult)

    // part 2
    val max = 70_000_000L
    val desiredSize = 30_000_000L
    val currentSize = finalFileToSize
        .asSequence()
        .filter { it.first.isDirectory }
        .map { it.first.name to it.second }.find { it.first == "/" }!!.second
    val remainder = max - currentSize
    val requiredSpace = desiredSize - remainder
    val part2Result = finalFileToSize
        .asSequence()
        .filter { it.first.isDirectory }
        .map { it.first.name to it.second }
        .filter { it.second >= requiredSpace }
        .sortedBy { it.second }.first().second
    println(part2Result)


}