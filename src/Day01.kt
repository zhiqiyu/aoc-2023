fun main() {
    val mapping = HashMap<String, Int>();
    mapping.put("one", 1);
    mapping.put("two", 2);
    mapping.put("three", 3);
    mapping.put("four", 4);
    mapping.put("five", 5);
    mapping.put("six", 6);
    mapping.put("seven", 7);
    mapping.put("eight", 8);
    mapping.put("nine", 9);

    fun getNumberFromLine(line: String): Int {
        var left = 0;
        var right = line.length - 1;
        while (left < right) {
            if (!line.get(left).isDigit()) {
                left += 1;
            }
            if (!line.get(right).isDigit()) {
                right -= 1;
            }
            if (line.get(left).isDigit() && line.get(right).isDigit()) break;
        }
        return line.get(left).digitToInt() * 10 + line.get(right).digitToInt();
    }

    fun checkSuffix(str: String): Int {
        for (i in (str.length - 5)..(str.length - 1)) {
            if (i < 0) continue;
            if (str.slice(i..str.length-1) in mapping) return mapping.getOrDefault(str.slice(i..str.length-1), 0);
        }
        return -1;
    }

    fun checkPrefix(str: String): Int {
        for (i in 0..4) {
            if (i >= str.length) break;
            if (str.slice(0..i) in mapping) return mapping.getOrDefault(str.slice(0..i), 0);
        }
        return -1;
    }

    fun getSpellingFromLine(line: String): Int {
        var left = 0;
        var right = line.length - 1;
        var leftResult = -1;
        var rightResult = -1;
        while (left <= right) {
            leftResult = if (line.get(left).isDigit()) line.get(left).digitToInt() else checkSuffix(line.slice(0..left))
            rightResult = if (line.get(right).isDigit()) line.get(right).digitToInt() else checkPrefix(line.slice(right..line.length-1))
            if (leftResult < 0) left += 1
            if (rightResult < 0) right -= 1
            if (leftResult >= 0 && rightResult >= 0) break
        }
        return leftResult * 10 + rightResult;
    }

    fun part1(input: List<String>): Int {
        return input.map { line -> getNumberFromLine(line) }.sum();
    }

    fun part2(input: List<String>): Int {
        return input.map { line -> getSpellingFromLine(line) }.sum();
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
