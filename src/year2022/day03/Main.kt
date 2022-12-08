package year2022.day03

import utils.runIt

fun main() = runIt(
	testInput = """
		vJrwpWtwJgWrhcsFMMfFFhFp
		jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
		PmmdzqPrVvPwwTWBwg
		wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
		ttgJtRGJQctTZtZT
		CrZsJsPPZsGzwwsLwLmpwMDw
	""".trimIndent(),
	::part1,
	testAnswerPart1 = 157,
	::part2,
	testAnswerPart2 = 70,
)

val priorities = (('a'..'z').toList() + ('A'..'Z').toList())
	.mapIndexed { i, c -> c to (i + 1) }
	.toMap()

fun splitInput(input: String) = input.split("\n")

fun intersection(vararg strings: String) = strings.reduce { shared, next ->
	shared.filter(next::contains)
}

fun part1(input: String) = splitInput(input).map { contents ->
	contents.chunked(contents.length / 2)
		.let { (a, b) -> intersection(a, b).first() }
		.let(priorities::getValue)
}.sum()

fun part2(input: String) = splitInput(input).chunked(3).map { threesome ->
	threesome
		.let { (a, b, c) -> intersection(a, b, c).first() }
		.let(priorities::getValue)
}.sum()
