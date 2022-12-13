package utils

import java.io.File
import java.io.InputStream
import java.net.URL

fun getInput(year: String, day: String) = File("src/year$year/day$day").resolve("input.txt").let {
	if (it.exists()) it.readText()
	else {
		val session = File(".session").let {
			check(it.exists()) { ".session file with cookie value must exist!" }
			it.readText().trim()
		}
		URL("https://adventofcode.com/$year/day/${day.trimStart('0')}/input").openConnection()
			.apply { setRequestProperty("Cookie", "session=$session") }
			.apply { setRequestProperty("User-Agent", "https://github.com/LewsTherinTelescope/advent-of-code-kotlin") }
			.getInputStream()
			.use(InputStream::readAllBytes)
			.also(it::writeBytes)
			.decodeToString()
	}
}.trimEnd('\n')

inline fun <One, Two> runIt(
	testInput: String,
	part1: (String) -> One,
	testAnswerPart1: One?,
	part2: (String) -> Two,
	testAnswerPart2: Two?,
	testInputPart2: String = testInput,
) {
	// in my defense, none of this is the most stable or efficient in the first place
	val (year, day) = StackWalker
		.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
		.callerClass
		.packageName
		.split('.')
		.let { (yearStr, dayStr) -> yearStr.removePrefix("year") to dayStr.removePrefix("day") }
	
	val realInput = getInput(year, day)

    if (testAnswerPart1 != null) {
        println("Part 1:")
        val testResult = part1(testInput)
        check(testResult == testAnswerPart1) { "Expected $testAnswerPart1, got $testResult" }
		println("Test passed!")
        println(part1(realInput))
    }

    if (testAnswerPart2 != null) {
        println("-".repeat(80))
        println("Part 2:")
        val testResult = part2(testInputPart2)
        check(testResult == testAnswerPart2) { "Expected $testAnswerPart2, got $testResult" }
		println("Test passed!")
        println(part2(realInput))
    }
}
