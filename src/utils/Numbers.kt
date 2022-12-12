package utils

import java.math.BigInteger
import java.math.BigInteger.ZERO as BIGINT_0

val Int.isEven get() = isDivisibleBy(2)
val Int.isOdd get() = !isDivisibleBy(2)
infix fun Int.isDivisibleBy(divisor: Int): Boolean = rem(divisor) == 0
infix fun Int.isDivisibleBy(divisor: Long): Boolean = rem(divisor) == 0L

val Long.isEven get() = isDivisibleBy(2)
val Long.isOdd get() = !isDivisibleBy(2)
infix fun Long.isDivisibleBy(divisor: Long): Boolean = rem(divisor) == 0L
infix fun Long.isDivisibleBy(divisor: Int): Boolean = rem(divisor) == 0L

infix fun Float.isDivisibleBy(divisor: Float): Boolean = rem(divisor) == 0.0F

infix fun Double.isDivisibleBy(divisor: Double): Boolean = rem(divisor) == 0.0

val BIGINT_2 = 2.toBigInteger()
val BigInteger.isEven get() = isDivisibleBy(BIGINT_2)
val BigInteger.isOdd get() = !isDivisibleBy(BIGINT_2)
infix fun BigInteger.isDivisibleBy(divisor: BigInteger): Boolean = rem(divisor) == BIGINT_0
infix fun BigInteger.isDivisibleBy(divisor: Int): Boolean = rem(bigint(divisor)) == BIGINT_0
infix fun BigInteger.isDivisibleBy(divisor: Long): Boolean = rem(bigint(divisor)) == BIGINT_0

fun bigint(value: Int): BigInteger = BigInteger.valueOf(value.toLong())
fun bigint(value: Long): BigInteger = BigInteger.valueOf(value)
