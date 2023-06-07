package play.vocabulary.english.change_one_letter

import play.vocab.playwithvocab.common.plugins.format
import play.vocab.playwithvocab.common.plugins.get
import play.vocab.playwithvocab.common.plugins.set
import play.vocab.playwithvocab.common.plugins.sortedAdd

class Game(val initialWord: String, val playerCount: Int = 2, val computerPlaysAs: List<Int> = listOf(0)) {
    private val wordSize = initialWord.length
    private val possibleWords = AllWords.getWordsOfSize(wordSize)!!.toMutableSet()
    private val moves = LinkedHashSet<Move>()
    private val usedWords = mutableListOf<String>()
    private var playerTurn = 0

    init {
        moves.add(Move(initialWord.uppercase(), 0))
    }

    private var expected = expectedWords()
    private var lastManualMoveIndex = -1

    init {
        autoMove()
    }

    private fun autoMove(): Boolean {
        return if (computerPlaysAs.contains(playerTurn)) {
            if (hasTurn()) {
                val out = expected.random()
                selectWord(out)
            } else {
                false
            }
        } else {
            false
        }
    }

    fun hasTurn(): Boolean =
        expected.isNotEmpty()

    private fun expectedWords(): List<String> =
        possibleWords.filter { word ->
            val mismatches = (0 until wordSize).count {
                word[it] != lastWord()[it]
            }

            mismatches == 1
        }

    fun move(index: Int, input: Char) {
        val word = StringBuffer(lastWord())
        word[index] = input.uppercaseChar()
        lastManualMoveIndex = moves.size
        selectWord(word.toString())
    }

    fun getLatestAutoMoves(): List<Move> =
        moves.filterIndexed { index, move ->
            index > lastManualMoveIndex
        }

    private fun lastWord(): String = moves.last().word

    private fun selectWord(word: String): Boolean =
        if (possibleWords.contains(word) && usedWords.binarySearch(word) < 0) {
            moves.add(Move(word, playerTurn + 1))
            usedWords.sortedAdd(word)
            possibleWords.remove(word)
            playerTurn = (playerTurn + 1) % playerCount
            expected = expectedWords()
            autoMove()
        } else {
            false
        }

    data class Move(val word: String, val player: Int) {
        override fun equals(other: Any?): Boolean {
            return (other is Move && word == other.word) || (other is String && word == other)
        }

        override fun hashCode(): Int {
            return word.hashCode()
        }
    }

    fun print(): Unit {
        println(" Step# | Player Name | ${" ".repeat((wordSize - 3) / 2)}Word ")
        println(" ----------- | ---- ")
        println(" ----- | Initial --> | ${moves.first().word}")
        (1 until moves.size).forEach {
            println(" ${it.format(5)} | Player-${moves[it].player.format(4)} | ${moves[it].word} ")
        }
    }
}