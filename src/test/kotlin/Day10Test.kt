import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class Day10Test {

    private val exampleInput = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]"
    )

    @Test
    fun examplePart1() {
        assertEquals(' ', validate("[]"))
        assertEquals(' ', validate("([])"))
        assertEquals(' ', validate("{()()()}"))
        assertEquals(' ', validate("<([{}])>"))
        assertEquals(' ', validate("[<>({}){}[([])<>]]"))
        assertEquals(' ', validate("(((((((((())))))))))"))
        assertEquals(']', validate("(]"))
        assertEquals('>', validate("{()()()>"))
        assertEquals('}', validate("(((()))}"))
        assertEquals(')', validate("<([]){()}[{}])"))

        val score = exampleInput.map { validate(it) }.sumOf { scoreOf(it) }
        assertEquals(26397, score)
    }

    private fun scoreOf(c: Char): Int {
        return when (c) {
            ']' -> 57
            ')' -> 3
            '>' -> 25137
            '}' -> 1197
            else -> 0
        }

    }

    private fun validate(input: String): Char {
        val stack = Stack<Char>()
        for (c in input) {
            when (c) {
                '[' -> stack.push(c)
                '(' -> stack.push(c)
                '<' -> stack.push(c)
                '{' -> stack.push(c)
                ']' -> if (!assertCharIs(stack, '[')) return c else stack.pop()
                ')' -> if (!assertCharIs(stack, '(')) return c else stack.pop()
                '>' -> if (!assertCharIs(stack, '<')) return c else stack.pop()
                '}' -> if (!assertCharIs(stack, '{')) return c else stack.pop()
            }
        }
        return ' ';
    }

    private fun assertCharIs(stack: Stack<Char>, c: Char): Boolean {
        return stack.peek() == c
    }


    @Test
    fun inputPart1() {
        val score = readFile().map { validate(it) }.sumOf { scoreOf(it) }
        println(score)
    }

    @Test
    fun examplePart2() {
        assertEquals("}}]])})]", complete(findStackToComplete("[({(<(())[]>[[{[]{<()<>>")!!).joinToString(""))
        assertEquals(")}>]})", complete(findStackToComplete("[(()[<>])]({[<{<<[]>>(")!!).joinToString(""))
        assertEquals("}}>}>))))", complete(findStackToComplete("(((({<>}<{<{<>}{[]{[]{}")!!).joinToString(""))
        assertEquals("]]}}]}]}>", complete(findStackToComplete("{<[[]]>}<{[{[{[]{()[[[]")!!).joinToString(""))
        assertEquals("])}>", complete(findStackToComplete("<{([{{}}[<[[[<>{}]]]>[]]")!!).joinToString(""))

        assertEquals(288957, scoreToComplete(complete(findStackToComplete("[({(<(())[]>[[{[]{<()<>>")!!)))
        assertEquals(5566, scoreToComplete(complete(findStackToComplete("[(()[<>])]({[<{<<[]>>(")!!)))
        assertEquals(1480781, scoreToComplete(complete(findStackToComplete("(((({<>}<{<{<>}{[]{[]{}")!!)))
        assertEquals(995444, scoreToComplete(complete(findStackToComplete("{<[[]]>}<{[{[{[]{()[[[]")!!)))
        assertEquals(294, scoreToComplete(complete(findStackToComplete("<{([{{}}[<[[[<>{}]]]>[]]")!!)))

        assertEquals(288957, middleScoreOf(listOf(288957, 5566, 1480781, 995444, 294).sorted()))
    }

    private fun middleScoreOf(listOf: List<Int>): Int {
        return listOf[listOf.size / 2]
    }

    private fun findStackToComplete(input: String): Stack<Char>? {
        val stack = Stack<Char>()
        for (c in input) {
            when (c) {
                '[' -> stack.push(c)
                '(' -> stack.push(c)
                '<' -> stack.push(c)
                '{' -> stack.push(c)
                ']' -> if (!assertCharIs(stack, '[')) return null else stack.pop()
                ')' -> if (!assertCharIs(stack, '(')) return null else stack.pop()
                '>' -> if (!assertCharIs(stack, '<')) return null else stack.pop()
                '}' -> if (!assertCharIs(stack, '{')) return null else stack.pop()
            }
        }
        return stack
    }

    private fun complete(stack: Stack<Char>): List<Char> {
        val charsToComplete = mutableListOf<Char>()
        for (c in stack) {
            when (c) {
                '[' -> charsToComplete.add(']')
                '(' -> charsToComplete.add(')')
                '<' -> charsToComplete.add('>')
                '{' -> charsToComplete.add('}')
            }
        }
        return charsToComplete.reversed()
    }

    private fun scoreToComplete(input: List<Char>): Long =
        input.fold(0L) { acc, char ->
            acc * 5 + scoreToComplete(char)
        }

    private fun scoreToComplete(c: Char): Int =
        when (c) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }


    @Test
    fun inputPart2() {
        val score = readFile().mapNotNull { findStackToComplete(it) }.map { complete(it) }.map { scoreToComplete(it) }
            .sorted()
        println(score[score.size / 2])
    }

    private fun readFile(): List<String> {
        return javaClass.getResource("10").readText().split(System.lineSeparator()).filter { it.isNotBlank() }
    }
}