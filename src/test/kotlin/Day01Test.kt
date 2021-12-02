import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {

    private val exampleInput = listOf(
        199,
        200,
        208,
        210,
        200,
        207,
        240,
        269,
        260,
        263
    )

    @Test
    fun examplePart1() {
        val result = countIncreases(exampleInput)
        assertEquals(7, result)
    }

    @Test
    fun inputPart1() {
        val inputNumbers = readFile().map { Integer.valueOf(it) }
        val result = countIncreases(inputNumbers)
        println(result)
    }

    @Test
    fun examplePart2() {
        val result = countIncreases(createSlidingWindows(exampleInput))
        assertEquals(5, result)
    }

    @Test
    fun inputPart2() {
        val inputNumbers = readFile().map { Integer.valueOf(it) }
        val result = countIncreases(createSlidingWindows(inputNumbers))
        println(result)
    }

    private fun countIncreases(inputNumbers: List<Int>): Int {
        return inputNumbers.foldIndexed(0) { index, acc, depth ->
            if (index == 0) {
                acc
            } else {
                val diff = if (depth > inputNumbers[index - 1]) 1 else 0
                acc + diff
            }
        }
    }

    private fun createSlidingWindows(inputNumbers: List<Int>): List<Int> {
        fun createSlidingWindowsRec(remainingNumbers: List<Int>, resultList: MutableList<Int>): List<Int> {
            return if (remainingNumbers.size < 3) {
                resultList
            } else {
                val slidingWindowSum = remainingNumbers.take(3).sum()
                resultList.add(slidingWindowSum)
                createSlidingWindowsRec(remainingNumbers.drop(1), resultList)
            }
        }
        return (createSlidingWindowsRec(inputNumbers, mutableListOf()))
    }

    private fun createSlidingWindowsFold(inputNumbers: List<Int>): List<Int> {
        return inputNumbers.foldIndexed(mutableListOf()) { index, acc, depth ->
            if (index < 2) {
                acc
            } else {
                acc.add(depth + inputNumbers[index - 1] + inputNumbers[index - 2])
                acc
            }
        }
    }

    private fun readFile(): List<String> {
        return javaClass.getResource("01").readText().split(System.lineSeparator()).filter { it.isNotBlank() }
    }
}