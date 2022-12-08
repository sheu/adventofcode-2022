package geometry

data class Point(val x: Long, val y: Long) {

    fun move(dir: Char): Point {
        return when (dir) {
            '^' -> {
                Point(x, y + 1)
            }
            'v' -> {
                Point(x, y - 1)
            }
            '>' -> {
                Point(x + 1, y)
            }
            '<' -> {
                Point(x - 1, y)
            }
            else -> error("Invalid direction")
        }
    }
}
