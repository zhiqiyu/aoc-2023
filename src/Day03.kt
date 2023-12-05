class Board(val input: List<List<Char>>) {
    val m: Int
    val n: Int
    val numberGrid: MutableList<MutableList<Int>>
    val parts: MutableList<Int>

    init {
        m = input.size
        n = input[0].size
        numberGrid = MutableList(m, { MutableList(n, { -1 }) })
        parts = mutableListOf<Int>()

        findParts()
        println(numberGrid)
        println(parts)
    }

    fun isSymbol(c: Char): Boolean {
        return !c.isDigit() && c != '.'
    }

    fun checkNeighbors(row: Int, colStart: Int, colEnd: Int): Boolean {
        if (row - 1 >= 0) {
            for (j in colStart - 1..colEnd + 1) {
                if (j < 0 || j >= n) continue
                if (isSymbol(input.get(row - 1).get(j))) return true
            }
        }
        if (colStart - 1 >= 0 && isSymbol(input.get(row).get(colStart - 1))) return true
        if (colEnd + 1 < n && isSymbol(input.get(row).get(colEnd + 1))) return true
        if (row + 1 < m) {
            for (j in colStart - 1..colEnd + 1) {
                if (j < 0 || j >= n) continue
                if (isSymbol(input.get(row + 1).get(j))) return true
            }
        }
        return false
    }

    fun findParts() {
        for (i in 0..m - 1) {
            var number = ""
            for (j in 0..n - 1) {
                if (!input.get(i).get(j).isDigit()) {
                    if (number != "" && checkNeighbors(i, j - number.length, j - 1)) {
                        for (p in number.indices) numberGrid[i][j - p - 1] = parts.size
                        parts.add(number.toInt())
                    }
                    number = ""
                } else {
                    number += input.get(i).get(j)
                }
            }
            if (number != "" && checkNeighbors(i, n - number.length, n - 1)) {
                for (p in number.indices) numberGrid[i][n - p - 1] = parts.size
                parts.add(number.toInt())
            }
        }
    }

    fun getGearPartsOrNull(i: Int, j: Int): Pair<Int, Int>? {
        val neighbors =
                arrayOf(
                        Pair(0, -1),
                        Pair(0, 1),
                        Pair(-1, -1),
                        Pair(-1, 0),
                        Pair(-1, 1),
                        Pair(1, -1),
                        Pair(1, 0),
                        Pair(1, 1)
                )
        val uniqueParts = mutableSetOf<Int>()
        neighbors.forEach {
            val ii = i + it.first
            val jj = j + it.second
            if (ii >= 0 && ii < m && jj >= 0 && jj < n && numberGrid[ii][jj] != -1)
                    uniqueParts.add(parts[numberGrid[ii][jj]])
        }

        if (uniqueParts.size == 2) return Pair(uniqueParts.toList()[0], uniqueParts.toList()[1])
        return null
    }

    fun getGearRatioSum(): Int {
        var result = 0
        for (i in 0..m - 1) {
            for (j in 0..n - 1) {
                if (input.get(i).get(j) == '*') {
                    val res = getGearPartsOrNull(i, j)
                    if (res !== null) {
                        result += res.first * res.second
                    }
                }
            }
        }
        return result
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val board = Board(input.map { line -> line.toCharArray().toList() })
        return board.parts.sum()
    }

    fun part2(input: List<String>): Int {
        val board = Board(input.map { line -> line.toCharArray().toList() })
        return board.getGearRatioSum()
    }

    val testInput = readInput("Day03_test")

    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
