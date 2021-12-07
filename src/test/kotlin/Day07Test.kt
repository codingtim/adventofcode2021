import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.abs

class Day07Test {

    private val exampleInput = "16,1,2,0,4,2,7,1,2,14"

    @Test
    fun examplePart1() {
        val parse = parse(exampleInput)
        val minFuelCost = minFuelCost(parse)
        assertEquals(37, minFuelCost)
    }

    private fun minFuelCost(parse: List<Int>): Int {
        val min = parse.minOrNull()!!
        val max = parse.maxOrNull()!!
        val minFuelCost = IntRange(min, max).map { position ->
            Pair(position, parse.sumOf { abs(position - it) })
        }.minByOrNull { it.second }!!.second
        return minFuelCost
    }

    private fun parse(input: String): List<Int> = input.split(",").map { it.toInt() }

    @Test
    fun inputPart1() {
        println(minFuelCost(parse(readFile())))
    }


    @Test
    fun examplePart2() {
        val parse = parse(exampleInput)
        val minFuelCost = minFuelCostIncreasingCost(parse)
        assertEquals(168, minFuelCost)
    }

    @Test
    fun inputPart2() {
        println(minFuelCostIncreasingCost(parse(readFile())))
    }

    private fun minFuelCostIncreasingCost(parse: List<Int>): Int {
        val min = parse.minOrNull()!!
        val max = parse.maxOrNull()!!
        val minFuelCost = IntRange(min, max).map { position ->
            Pair(position, parse.sumOf { IntRange(1, abs(position - it)).sum() })
        }.minByOrNull { it.second }!!.second
        return minFuelCost
    }

    private fun readFile(): String {
        return javaClass.getResource("07").readText()
    }
}