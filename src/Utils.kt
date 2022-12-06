package utils

import java.io.File
import java.io.InputStream
import java.net.URL

fun getInput(year: String, day: String) = File("src/year$year/day$day").resolve("input.txt").let {
	if (it.exists()) it.readText().trimEnd('\n')
	else {
		val session = File(".session").let {
			check(it.exists()) { ".session file with cookie value must exist!" }
			it.readText().trim()
		}
		URL("https://adventofcode.com/$year/day/${day.trimStart('0')}/input").openConnection()
			.apply { setRequestProperty("Cookie", "session=$session") }
			.getInputStream()
			.use(InputStream::readAllBytes)
			.also(it::writeBytes)
			.decodeToString()
	}
}

typealias AoC = (String) -> Int
fun <One, Two> runIt(
	year: String,
	day: String,
	testInput: String,
	part1: (String) -> One,
	testAnswerPart1: One?,
	part2: (String) -> Two,
	testAnswerPart2: Two?
) {
	val realInput = getInput(year, day)

    if (testAnswerPart1 != null) {
        println("Part 1:")
        val testResultPart1 = part1(testInput)
        check(testResultPart1 == testAnswerPart1) { "Expected $testAnswerPart1, got $testResultPart1" }
        println(part1(realInput))
    }

    if (testAnswerPart2 != null) {
        println("-".repeat(80))
        println("Part 2:")
        val testResultPart2 = part2(testInput)
        check(testResultPart2 == testAnswerPart2) { "Expected $testAnswerPart2, got $testResultPart2" }
        println(part2(realInput))
    }
}
