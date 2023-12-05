class Card(val input: String) {
    val id: Int
    val winning: Set<Int>
    val having: Set<Int>

    init {
        val idPattern = Regex("""Card\s+(\d+)""")
        id = idPattern.find(input)!!.groupValues[1].toInt()

        val pattern1 = Regex(""":((\s+\d+)*)\s\|""")
        val match1 = pattern1.find(input)
        winning = match1!!.groupValues[1].trim().split(' ').filter { it != "" }.map { it.toInt() }.toSet()

        val pattern2 = Regex("""\|((\s+\d+)*)""")

        val match2 = pattern2.find(input)
        having = match2!!.groupValues[1].trim().split(' ').filter { it != "" }.map { it.toInt() }.toSet()
    }

    fun getPoint(): Int {
        var matchCount = getNumberOfMatches()
        return if (matchCount > 0) Math.pow(2.0, matchCount-1.0).toInt() else 0
    }

    fun getNumberOfMatches(): Int {
        var matchCount = 0
        for (w in winning) {
            if (w in having) matchCount += 1
        }
        return matchCount
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val cards = input.map { Card(it) }
        return cards.map { it.getPoint() }.sum()
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { Card(it) }.sortedBy { it.id }
        val copies = MutableList(cards.size, { 1 })

        for (i in 0..cards.size-2) {
            for (j in 1..cards[i].getNumberOfMatches()) {
                copies[i+j] += copies[i]
            }
        }
        return copies.sum()
    }

    val testInput = readInput("Day04_test")

    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}