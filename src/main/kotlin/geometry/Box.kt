package geometry

data class Box(val l: Long, val w: Long, val h: Long)

fun Box.toWrappingArea(): Long {
    val areas = listOf(l * w, h * w, h * l)
    return 2 * areas.sum() + areas.min()
}

fun Box.shortestPerimeter(): Long {
    return listOf(l, w, h).sorted().dropLast(1).sum() * 2
}

fun Box.volume(): Long {
    return l * w * h
}