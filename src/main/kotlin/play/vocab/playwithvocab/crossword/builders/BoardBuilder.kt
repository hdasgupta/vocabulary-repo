package play.vocab.playwithvocab.crossword.builders

import play.vocab.playwithvocab.crossword.commons.Board
import play.vocab.playwithvocab.crossword.commons.InsertOption
import play.vocab.playwithvocab.crossword.enums.WordDirection

class BoardBuilder(
    private val enableCyclic: Boolean = false,
    private val enableAdjacent: Boolean = false
) {
    private var boards: Set<Board> = setOf(Board(listOf(), enableCyclic, enableAdjacent))

    fun with(word: String, desc: String): BoardBuilder {
        boards = WordDirection.values().map {
            InsertOption(
                word,
                desc,
                it,
                enableCyclic,
                enableAdjacent
            )
        }.flatMap {
            boards.flatMap {
                board->
                    board.insert(it)
            }
        }.toSet()
        return this;
    }

    fun build(): Set<Board> = boards

    fun build(wordsWithDesc: Map<String, String>): Set<Board> {
        wordsWithDesc.forEach {
            this.with(it.key, it.value)
        }
        return this.build()
    }

}