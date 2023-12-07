class Comparator() {
    val order = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val counts = getCardCounts()

    fun getHighestLevel(): Int {
        if (hasFive()) return 7
        if (hasFour()) return 6
        if (hasFullHouse()) return 5
        if (hasThree()) return 4
        if (hasTwo()) return 3
        if (hasOne()) return 2
        if (hasHighCard()) return 1
        return 0
    }

    fun hasFive(): Boolean {
        return input.toSet().size == 1
    }

    fun hasFour(): Boolean {
        return input.toSet().size == 2 && counts.values.indexOf(1) >= 0
    }

    private fun getCardCounts(): MutableMap<Char, Int> {
        var counts = mutableMapOf<Char, Int>()
        input.forEach { counts.set(it, counts.getOrDefault(it, 0) + 1)}
        return counts
    }

    fun hasFullHouse(): Boolean {
        return counts.values.indexOf(3) >= 0 && counts.size == 2
    }

    fun hasThree(): Boolean {
        return counts.values.indexOf(3) >= 0
    }

    fun hasTwo(): Boolean {
        return counts.size == 3 && counts.values.indexOf(1) >= 0
    }

    fun hasOne(): Boolean {
        return counts.size == 4
    }

    fun hasHighCard(): Boolean {
        return counts.size == 5
    }
}

class Hand(val input: String, val bid: Double) {
    val order = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val counts = getCardCounts()

    fun compareTo(other: Hand): Int {
        if (input == other.input) return 0
        val myHighest = getHighestLevel()
        val otherHighest = other.getHighestLevel()
        if (myHighest > otherHighest) return 1
        else if (myHighest < otherHighest) return -1
        
        for (i in 0..4) {
            if (order.indexOf(input[i]) > order.indexOf(other.input[i])) return 1
            else if (order.indexOf(input[i]) < order.indexOf(other.input[i])) return -1
        }
        return 0
    }

    fun getHighestLevel(): Int {
        if (hasFive()) return 7
        if (hasFour()) return 6
        if (hasFullHouse()) return 5
        if (hasThree()) return 4
        if (hasTwo()) return 3
        if (hasOne()) return 2
        if (hasHighCard()) return 1
        return 0
    }

    fun hasFive(): Boolean {
        return input.toSet().size == 1
    }

    fun hasFour(): Boolean {
        return input.toSet().size == 2 && counts.values.indexOf(1) >= 0
    }

    private fun getCardCounts(): MutableMap<Char, Int> {
        var counts = mutableMapOf<Char, Int>()
        input.forEach { counts.set(it, counts.getOrDefault(it, 0) + 1)}
        return counts
    }

    fun hasFullHouse(): Boolean {
        return counts.values.indexOf(3) >= 0 && counts.size == 2
    }

    fun hasThree(): Boolean {
        return counts.values.indexOf(3) >= 0
    }

    fun hasTwo(): Boolean {
        return counts.size == 3 && counts.values.indexOf(1) >= 0
    }

    fun hasOne(): Boolean {
        return counts.size == 4
    }

    fun hasHighCard(): Boolean {
        return counts.size == 5
    }
}

fun main() {

    fun part1(input: List<String>): Double {
        val hands = mutableListOf<Hand>()
        input.forEach { 
            var t = it.split(" ").map { it.trim() }
            hands.add(Hand(t[0], t[1].toDouble()))
        }

        var result: Double = 0.0
        var points = MutableList<Double>(input.size, { 1.0 })
        for (i in input.indices) {
            for (j in i+1..input.size-1) {
                var r = hands[i].compareTo(hands[j])
                when (r) {
                    1 -> points[i] += 1.0
                    -1 -> points[j] += 1.0
                    else -> println("!!WRONG")
                }
            }
        }
        for (i in input.indices) {
            result += hands[i].bid * points[i]
        }
        return result
    }

    fun part2(input: List<String>): Long {
        
        
    }

    val testInput = readInput("Day07_test")

    check(part1(testInput) == 6440.0)
    // check(part2(testInput) == 71503L)

    val input = readInput("Day07")
    part1(input).println()
    // part2(input).println()
}