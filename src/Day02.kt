data class Draw(val red: Int, val green: Int, val blue: Int)

class Game(val input: String) {
    val number: Int
    val draws: List<Draw>
    
    init {
        var step1 = input.split(':').map { s -> s.trim() }
        number = step1[0].split(' ')[1].toInt()
        var step2 = step1[1].split(';').map {s -> s.trim()}
        draws = ArrayList<Draw>()
        for (set in step2) {
            var step3 = set.split(',').map { s -> s.trim() }
            var red = 0;
            var blue = 0;
            var green = 0
            for (item in step3) {
                var step4 = item.split(' ')
                var count = step4[0].toInt()
                when (step4[1]) {
                    "red" -> red = count
                    "blue" -> blue = count
                    "green" -> green = count
                }
            }
            draws.add(Draw(red, green, blue))
        }
    }

    fun checkPossible(set: Draw): Boolean {
        for (draw in draws) {
            if (draw.red > set.red || draw.green > set.green || draw.blue > set.blue) return false;
        }
        return true;
    }

    fun getMinimumPossible(): Int {
        var red = Int.MIN_VALUE
        var green = Int.MIN_VALUE
        var blue = Int.MIN_VALUE
        for (draw in draws) {
            red = Math.max(red, draw.red)
            green = Math.max(green, draw.green)
            blue = Math.max(blue, draw.blue)
        }
        return red * green * blue
    }
}

fun main() {
    

    fun part1(input: List<String>, set: Draw): Int {
        return input.map { line -> Game(line) }.filter {
            game -> game.checkPossible(set)
        }.map { game -> game.number}.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { line -> Game(line) }.map { game -> game.getMinimumPossible() }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput, Draw(12, 13, 14)) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input, Draw(12, 13, 14)).println()
    part2(input).println()
}