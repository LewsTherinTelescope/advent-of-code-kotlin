package utils

import kotlin.experimental.ExperimentalTypeInference
import java.math.BigInteger
import java.util.LinkedList

fun <E> Collection<E>.toLinkedList(): LinkedList<E> = LinkedList(this)

fun List<Int>.product(): Int = reduce(Int::times)
fun List<Long>.product(): Long = reduce(Long::times)
fun List<Float>.product(): Float = reduce(Float::times)
fun List<Double>.product(): Double = reduce(Double::times)
fun List<BigInteger>.product(): BigInteger = reduce(BigInteger::times)

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int =
	fold(1) { product, it -> product * selector(it) }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long =
	fold(1L) { product, it -> product * selector(it) }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Float): Float =
	fold(1.0F) { product, it -> product * selector(it) }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Double): Double =
	fold(1.0) { product, it -> product * selector(it) }

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> BigInteger): BigInteger =
	fold(BigInteger.ONE) { product, it -> product * selector(it) }

/**
 * Like [forEach], but removes each element from the list as it is iterated over.
 * 
 * Intended for stack-like cases where each element needs to be popped in order.
 */
inline fun <T> MutableList<T>.removeEach(action: (T) -> Unit): Unit {
	while(isNotEmpty()) action(removeFirst())
}
