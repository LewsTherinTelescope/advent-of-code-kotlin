@file:Suppress("NOTHING_TO_INLINE")

package utils

import java.math.BigInteger
import java.math.BigInteger.ZERO as BIGINT_0

inline val Int.isEven get() = isDivisibleBy(2)
inline val Int.isOdd get() = !isDivisibleBy(2)
inline infix fun Int.isDivisibleBy(divisor: Int): Boolean = rem(divisor) == 0
inline infix fun Int.isDivisibleBy(divisor: Long): Boolean = rem(divisor) == 0L

inline val Long.isEven get() = isDivisibleBy(2)
inline val Long.isOdd get() = !isDivisibleBy(2)
inline infix fun Long.isDivisibleBy(divisor: Long): Boolean = rem(divisor) == 0L
inline infix fun Long.isDivisibleBy(divisor: Int): Boolean = rem(divisor) == 0L

inline infix fun Float.isDivisibleBy(divisor: Float): Boolean = rem(divisor) == 0.0F

inline infix fun Double.isDivisibleBy(divisor: Double): Boolean = rem(divisor) == 0.0

val BIGINT_2 = 2.toBigInteger()
inline val BigInteger.isEven get() = isDivisibleBy(BIGINT_2)
inline val BigInteger.isOdd get() = !isDivisibleBy(BIGINT_2)
inline infix fun BigInteger.isDivisibleBy(divisor: BigInteger): Boolean = rem(divisor) == BIGINT_0
inline infix fun BigInteger.isDivisibleBy(divisor: Int): Boolean = rem(bigint(divisor)) == BIGINT_0
inline infix fun BigInteger.isDivisibleBy(divisor: Long): Boolean = rem(bigint(divisor)) == BIGINT_0

inline fun bigint(value: Int): BigInteger = BigInteger.valueOf(value.toLong())
inline fun bigint(value: Long): BigInteger = BigInteger.valueOf(value)
