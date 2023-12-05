data class Record(val destStart: Long, val srcStart: Long, val length: Long)

class Mapping(val records: List<Record>) {
    fun get(v: Long): Long {
        for (r in records) {
            // prLongln(String.format("%d %d %d", r.destStart, r.srcStart, r.length))
            if (v >= r.srcStart && v < r.srcStart + r.length) {
                return v - r.srcStart + r.destStart
            }
        }
        return v;
    }
}

class Plan(val input: List<String>) {
    val s2sMap: Mapping
    val s2fMap: Mapping
    val f2wMap: Mapping
    val w2lMap: Mapping
    val l2tMap: Mapping
    val t2hMap: Mapping
    val h2lMap: Mapping

    init {
        val s2s = input.indexOf("seed-to-soil map:")
        val s2f = input.indexOf("soil-to-fertilizer map:")
        val f2w = input.indexOf("fertilizer-to-water map:")
        val w2l = input.indexOf("water-to-light map:")
        val l2t = input.indexOf("light-to-temperature map:")
        val t2h = input.indexOf("temperature-to-humidity map:")
        val h2l = input.indexOf("humidity-to-location map:")

        s2sMap = Mapping(input.subList(s2s+1, s2f-1).map { it.split(" ").map { it.toLong() } }.map { Record(it[0], it[1], it[2]) })
        s2fMap = Mapping(input.subList(s2f+1, f2w-1).map { it.split(" ").map { it.toLong() } }.map { Record(it[0], it[1], it[2]) })
        f2wMap = Mapping(input.subList(f2w+1, w2l-1).map { it.split(" ").map { it.toLong() } }.map { Record(it[0], it[1], it[2]) })
        w2lMap = Mapping(input.subList(w2l+1, l2t-1).map { it.split(" ").map { it.toLong() } }.map { Record(it[0], it[1], it[2]) })
        l2tMap = Mapping(input.subList(l2t+1, t2h-1).map { it.split(" ").map { it.toLong() } }.map { Record(it[0], it[1], it[2]) })
        t2hMap = Mapping(input.subList(t2h+1, h2l-1).map { it.split(" ").map { it.toLong() } }.map { Record(it[0], it[1], it[2]) })
        h2lMap = Mapping(input.subList(h2l+1, input.size).map { it.split(" ").map { it.toLong() } }.map { Record(it[0], it[1], it[2]) })
    }

    fun getLocation(seed: Long): Long {
        return h2lMap.get(t2hMap.get(l2tMap.get(w2lMap.get(f2wMap.get(s2fMap.get(s2sMap.get(seed)))))))
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        var match = Regex("""seeds:((\s+\d+)*)""").find(input[0])!!.groupValues
        val seeds = match[1].trim().split(' ').map { it.trim().toLong() }
        val p = Plan(input)
        var loc = Long.MAX_VALUE
        for (s in seeds) {
            loc = Math.min(loc, p.getLocation(s))
        }
        return loc
    }

    fun part2(input: List<String>): Long {
        var match = Regex("""seeds:((\s+\d+)*)""").find(input[0])!!.groupValues
        val seedRanges = match[1].trim().split(' ').map { it.trim().toLong() }
        val seeds = sequence {
            for (i in seedRanges.indices step 2) {
                yieldAll(generateSequence(seedRanges[i]) { 
                    it + 1 
                }.takeWhile({ it -> it < seedRanges[i] + seedRanges[i+1]-1 }))
            }
        }   
        val p = Plan(input)
        var loc = Long.MAX_VALUE
        for (s in seeds) {
            loc = Math.min(loc, p.getLocation(s))
        }
        println(loc)
        return loc
    }

    val testInput = readInput("Day05_test")

    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}