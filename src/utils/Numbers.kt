package utils

import java.math.BigInteger

val Int.isEven get() = this % 2 == 0
val Int.isOdd get() = this % 2 != 0
infix fun Int.isDivisibleBy(divisor: Int): Boolean = this % divisor == 0
infix fun Int.isDivisibleBy(divisor: Long): Boolean = this % divisor == 0L

val Long.isEven get() = this % 2 == 0L
val Long.isOdd get() = this % 2 != 0L
infix fun Long.isDivisibleBy(divisor: Long): Boolean = this % divisor == 0L
infix fun Long.isDivisibleBy(divisor: Int): Boolean = this % divisor == 0L

infix fun Float.isDivisibleBy(divisor: Float): Boolean = this % divisor == 0.0F

infix fun Double.isDivisibleBy(divisor: Double): Boolean = this % divisor == 0.0

infix fun BigInteger.isDivisibleBy(divisor: BigInteger): Boolean = this % divisor == BigInteger.ZERO
