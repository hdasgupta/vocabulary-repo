package play.vocab.playwithvocab.crossword

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import play.vocab.playwithvocab.crossword.builders.BoardBuilder

@SpringBootTest
class CrosswordServiceTest {
    @Test
    fun boardTest() {
        val words = mapOf(
            Pair("test", "check"),
            Pair("last", "at end"),
            Pair("list", "collection"),
            Pair("fast", "quick")
        )
        val boardBuilder = BoardBuilder(
            enableCyclic = true,
            enableAdjacent = true
        )

        val boards = boardBuilder.build(words)

        val boardSummaries = boards.map { board -> board.summary()}

        boardSummaries.forEach {
            println("Words LTR:")
            it.wordsLeftToRight.forEach {
                word->
                println("index = ${word.index}, word = ${word.word}, desc = ${word.description}")
            }
            println("Words TTB:")
            it.wordsLeftToRight.forEach {
                    word->
                println("index = ${word.index}, word = ${word.word}, desc = ${word.description}")
            }

        }
    }
}