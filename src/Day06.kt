class Race(val maxTime: Long, val bestDistance: Long) {
    fun getWaysToBeat(): Long {
        var result = 0L
        val r = generateSequence(0) { it + 1}.takeWhile { it <= maxTime }
        for (i in  r) {
            if (i * (maxTime - i) > bestDistance) result += 1
        }
        return result
    }
}

fun main() {

    fun part1(input: List<String>): Long {
        val groups1 = Regex("""Time:((\s+\d+)+)""").matchEntire(input[0])!!.groupValues
        val groups2 = Regex("""Distance:((\s+\d+)+)""").matchEntire(input[1])!!.groupValues

        val times = groups1[1].trim().split(" ").filter { it != ""}.map { it.trim().toLong() }
        val distances = groups2[1].trim().split(" ").filter { it != ""}.map { it.trim().toLong() }
        
        val ways = times.zip(distances).map { Race(it.first, it.second).getWaysToBeat() }
        var result = 1L
        ways.forEach { result = it * result }
        return result
    }

    fun part2(input: List<String>): Long {
        val groups1 = Regex("""Time:((\s+\d+)+)""").matchEntire(input[0])!!.groupValues
        val groups2 = Regex("""Distance:((\s+\d+)+)""").matchEntire(input[1])!!.groupValues

        val times = groups1[1].trim().split(" ").filter { it != "" }.map { it.trim() }.joinToString("").toLong()
        val distances = groups2[1].trim().split(" ").filter {it != "" }.map { it.trim() }.joinToString("").toLong()

        return Race(times, distances).getWaysToBeat().toLong()
        
    }

    val testInput = readInput("Day06_test")

    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}