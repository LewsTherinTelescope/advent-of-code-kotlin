package utils

import java.math.BigInteger

fun CharSequence.splitOnBlankLines(): List<String> = split("\n\n")
fun CharSequence.chunkOnBlankLines(): List<List<String>> = split("\n\n").map(CharSequence::lines)

fun CharSequence.extractTrailingInt(): Int = trim().split(" ").last().toInt()
fun CharSequence.extractTrailingLong(): Long = trim().split(" ").last().toLong()
fun CharSequence.extractTrailingFloat(): Float = trim().split(" ").last().toFloat()
fun CharSequence.extractTrailingDouble(): Double = trim().split(" ").last().toDouble()
fun CharSequence.extractTrailingBigInteger(): BigInteger = trim().split(" ").last().toBigInteger()

fun CharSequence.toIntArray(): IntArray = split(" ")
	.let { split -> IntArray(split.size) { split[it].toInt() } }
fun CharSequence.toLongArray(): LongArray = split(" ")
	.let { split -> LongArray(split.size) { split[it].toLong() } }
fun CharSequence.toFloatArray(): FloatArray = split(" ")
	.let { split -> FloatArray(split.size) { split[it].toFloat() } }
fun CharSequence.toDoubleArray(): DoubleArray = split(" ")
	.let { split -> DoubleArray(split.size) { split[it].toDouble() } }
fun CharSequence.toBigIntegerArray(): Array<BigInteger> = split(" ")
	.let { split -> Array<BigInteger>(split.size) { split[it].toBigInteger() } }

fun CharSequence.toIntList(delimiter: String = ", "): List<Int> =
	split(delimiter).map(String::toInt)
fun CharSequence.toLongList(delimiter: String = ", "): List<Long> =
	split(delimiter).map(String::toLong)
fun CharSequence.toFloatList(delimiter: String = ", "): List<Float> =
	split(delimiter).map(String::toFloat)
fun CharSequence.toDoubleList(delimiter: String = ", "): List<Double> =
	split(delimiter).map(String::toDouble)
fun CharSequence.toBigIntegerList(delimiter: String = ", "): List<BigInteger> =
	split(delimiter).map(String::toBigInteger)
