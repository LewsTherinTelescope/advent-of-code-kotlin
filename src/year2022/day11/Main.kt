package year2022.day11

import utils.bigint
import utils.extractTrailingInt
import utils.isDivisibleBy
import utils.productOf
import utils.removeEach
import utils.runIt
import utils.splitOnBlankLines
import utils.toLinkedList
import utils.toLongList
import java.math.BigInteger

fun main() = runIt(
	testInput = """
		Monkey 0:
		  Starting items: 79, 98
		  Operation: new = old * 19
		  Test: divisible by 23
		    If true: throw to monkey 2
		    If false: throw to monkey 3

		Monkey 1:
		  Starting items: 54, 65, 75, 74
		  Operation: new = old + 6
		  Test: divisible by 19
		    If true: throw to monkey 2
		    If false: throw to monkey 0

		Monkey 2:
		  Starting items: 79, 60, 97
		  Operation: new = old * old
		  Test: divisible by 13
		    If true: throw to monkey 1
		    If false: throw to monkey 3

		Monkey 3:
		  Starting items: 74
		  Operation: new = old + 3
		  Test: divisible by 17
		    If true: throw to monkey 0
		    If false: throw to monkey 1
	""".trimIndent(),
	::part1,
	testAnswerPart1 = bigint(10605),
	::part2,
	testAnswerPart2 = bigint(2713310158),
)

fun part1(input: String): BigInteger = input.execMonkeyBusiness(rounds = 20)

fun part2(input: String): BigInteger = input.execMonkeyBusiness(rounds = 10_000, canChill = false)

fun String.execMonkeyBusiness(
	rounds: Int,
	canChill: Boolean = true,
) = splitOnBlankLines()
	.map(Monke::from)
	.calculateInspectionCounts(rounds, canChill)
	.apply { sortDescending() }
	.take(2)
	.productOf(Int::toBigInteger)

typealias WorryLevel = Long

data class Monke(
	val startingItems: List<WorryLevel>,
	val increaseGivenWorry: (WorryLevel) -> WorryLevel,
	val testDivisor: Int,
	val targetIfTrue: Int,
	val targetIfFalse: Int,
) {
	companion object {
		fun from(input: String): Monke = input.lines().let { lines ->
			Monke(
				startingItems = lines[1].split(": ").last().trim().toLongList(),
				increaseGivenWorry = lines[2].toAperation(),
				testDivisor = lines[3].extractTrailingInt(),
				targetIfTrue = lines[4].extractTrailingInt(),
				targetIfFalse = lines[5].extractTrailingInt(),
			)
		}
	}
	
	fun test(worryLevel: WorryLevel): Boolean = worryLevel isDivisibleBy testDivisor
}

fun List<Monke>.calculateInspectionCounts(
	rounds: Int,
	canChillax: Boolean = true,
): IntArray {
	val lcm = productOf { it.testDivisor }
	val monkeInspectionCounts = IntArray(size)
	val monkeItems = Array(size) { get(it).startingItems.toLinkedList() }
	val monkesWithItems = zip(monkeItems)
	
	fun Monke.yeet(worryLevel: WorryLevel) {
		val target = if (test(worryLevel)) targetIfTrue else targetIfFalse
		monkeItems[target] += worryLevel
	}
	
	repeat(rounds) {
		monkesWithItems.forEachIndexed { i, (monke, itemWorries) ->
			itemWorries.removeEach { initialWorry ->
				monke.increaseGivenWorry(initialWorry)
					.let { (if (canChillax) it / 3 else it) % lcm }
					.let(monke::yeet)
				monkeInspectionCounts[i]++
			}
		}
	}
	
	return monkeInspectionCounts
}

fun String.toAperation(): (WorryLevel) -> WorryLevel = split(" ")
	.takeLast(2)
	.let { (op, modifier) ->
		when (op) {
			"+" -> { old: WorryLevel -> old + (modifier.toLongOrNull() ?: old) }
			"*" -> { old: WorryLevel -> old * (modifier.toLongOrNull() ?: old) }
			else -> error("Unrecognized operation: $op")
		}
	}
