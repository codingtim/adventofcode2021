import Day02Test.CommandParser.parse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {

    private val exampleInput = listOf(
        "forward 5",
        "down 5",
        "forward 8",
        "up 3",
        "down 8",
        "forward 2"
    )

    @Test
    fun examplePart1() {
        val result = exampleInput
            .map { parse(it) }
            .fold(Position(0, 0)) { position, command ->
                position.execute(command)
            }
        assertEquals(Position(15, 10), result)
    }

    @Test
    fun inputPart1() {
        val result = readFile()
            .map { parse(it) }
            .fold(Position(0, 0)) { position, command ->
                position.execute(command)
            }
        println(result.horizontalPosition * result.depth)
    }

    private data class Position(val horizontalPosition: Int, val depth: Int) {
        fun execute(command: Command): Position =
            when (command) {
                is Forward -> Position(horizontalPosition + command.delta, depth)
                is Down -> Position(horizontalPosition, depth + command.delta)
                is Up -> Position(horizontalPosition, depth - command.delta)
                is NoOp -> Position(horizontalPosition, depth)
            }
    }

    @Test
    fun examplePart2() {
        val result = exampleInput
            .map { parse(it) }
            .fold(PositionWithAim(Position(0, 0), 0)) { position, command ->
                position.execute(command)
            }
        assertEquals(Position(15, 60), result.position)
    }

    @Test
    fun inputPart2() {
        val result = readFile()
            .map { parse(it) }
            .fold(PositionWithAim(Position(0, 0), 0)) { position, command ->
                position.execute(command)
            }
        println(result.position.horizontalPosition * result.position.depth)
    }

    sealed interface Command

    data class Forward(val delta: Int) : Command
    data class Down(val delta: Int) : Command
    data class Up(val delta: Int) : Command
    data class NoOp(val delta: Int) : Command

    object CommandParser {
        fun parse(input: String): Command {
            val commandSplit = input.split(" ")
            return when (commandSplit[0]) {
                "forward" -> Forward(commandSplit[1].toInt())
                "down" -> Down(commandSplit[1].toInt())
                "up" -> Up(commandSplit[1].toInt())
                else -> NoOp(0)
            }
        }
    }

    private data class PositionWithAim(val position: Position, val aim: Int) {
        fun execute(command: Command): PositionWithAim =
            when (command) {
                is Forward -> PositionWithAim(Position(position.horizontalPosition + command.delta, position.depth + (command.delta * aim)), aim)
                is Down -> PositionWithAim(Position(position.horizontalPosition, position.depth), aim + command.delta)
                is Up -> PositionWithAim(Position(position.horizontalPosition, position.depth), aim - command.delta)
                is NoOp -> PositionWithAim(Position(position.horizontalPosition, position.depth), aim)
            }
    }

    private fun readFile(): List<String> {
        return javaClass.getResource("02").readText().split(System.lineSeparator()).filter { it.isNotBlank() }
    }
}