package play.vocab.playwithvocab.change_one_letter.services

import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.Size
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import play.vocab.playwithvocab.change_one_letter.dto.BasicGameDto
import play.vocab.playwithvocab.change_one_letter.dto.GameDto
import play.vocab.playwithvocab.change_one_letter.dto.TurnDto
import play.vocab.playwithvocab.change_one_letter.entiries.COLGame
import play.vocab.playwithvocab.change_one_letter.entiries.WordUsedInGame
import play.vocab.playwithvocab.change_one_letter.repos.COLGameRepo
import play.vocab.playwithvocab.change_one_letter.repos.WordUsedRepo
import play.vocab.playwithvocab.common.plugins.randomWord
import play.vocab.playwithvocab.common.plugins.set
import play.vocab.playwithvocab.common.repos.WordRepo

@Service
@Validated
class COLGameService(
    private var gameRepo: COLGameRepo,
    private var wordRepo: WordRepo,
    private var wordUsedRepo: WordUsedRepo
) {


    fun getAll(): List<BasicGameDto> =
        gameRepo.findAll().map { game -> getBasicGame(game.reference) }


    fun newGame(
        @Valid @Size(min = 3) initial: String,
        @Valid @Min(2) playerCount: Int,
        computerPlaysAs: Set<Int>
    ): GameDto? {
        return if (computerPlaysAs.all { it in 0 until playerCount }) {
            val word = wordRepo.findByWordIgnoreCase(initial)
            val game = COLGame(
                initial = word!!,
                playerCount = playerCount,
                computerPlaysAs = computerPlaysAs
            )

            //game = gameRepo.save(game)

            val usedInGame = WordUsedInGame(
                game = game,
                word = wordRepo.findByWordIgnoreCase(initial)!!,
                player = game.playerTurn
            )

            game.playerTurn = (game.playerTurn + 1) % game.playerCount

            gameRepo.save(game)
            wordUsedRepo.save(usedInGame)

            autoMove(game.reference)

            getGame(game.reference)

        } else {
            null
        }

    }

    fun randomWord(min:Int = 3, max:Int = 8): String = wordRepo.randomWord(min, max)

    fun getGame(reference: String): GameDto {
        val usedWords = usedWords(reference)
            .reversed();
        val availableChars = availableChars(reference)
            .mapIndexed {
                index, chars ->
                val mutable = chars
                    .map {
                        it.uppercaseChar()
                    }
                    .toMutableList()
                mutable.remove(usedWords[0].chars[index])
                mutable.toList()
            }

        return GameDto(
            reference,
            usedWords,
            availableChars
        )
    }

    fun getBasicGame(reference: String): BasicGameDto {
        val game = gameRepo.findByReference(reference)
        //val hasAvailableChars = wordRepo.hasValidList(wordUsedRepo.lastWord(gameId).id!!)

        return BasicGameDto(
            reference,
            game.initial.word.uppercase(),
            hasTurn(reference)
        )
    }

    private fun hasTurn(reference: String): Boolean =
        expectedWords(reference).isNotEmpty()

    private fun autoMove(reference: String): Boolean {
        val game = gameRepo.findByReference(reference)
        return if (game.computerPlaysAs.contains(game.playerTurn)) {
            val expected = expectedWords(game.reference)
            if (expected.isNotEmpty()) {
                val out = expected.random()
                selectWord(game, out, expected)
            } else {
                false
            }
        } else {
            false
        }
    }

    fun move(reference: String, index: Int, input: Char): Boolean {
        val game = gameRepo.findByReference(reference)
        val word = wordUsedRepo.lastWord(reference)
        val buffer = StringBuffer(word.word)
        val expected = expectedWords(game.reference)
        buffer[index] = input.lowercaseChar()
        game.lastManualMoveIndex = game.usedWords.size
        return selectWord(game, buffer.toString(), expected)
    }


    private fun selectWord(game: COLGame, word: String, expected: List<String>): Boolean {
        return if (expected.contains(word)) {
            val usedInGame = WordUsedInGame(
                game = game,
                word = wordRepo.findByWordIgnoreCase(word)!!,
                player = game.playerTurn
            )

            wordUsedRepo.save(usedInGame)

            game.playerTurn = (game.playerTurn + 1) % game.playerCount

            gameRepo.save(game)

            autoMove(game.reference)

        } else {
            false
        }
    }

    fun availableChars(reference: String): List<List<Char>> {
        val game = gameRepo.findByReference(reference)
        val wordSize = game.initial.size
        val validWords = expectedWords(reference)
        return (0 until wordSize).map {
            validWords.map { word ->
                word[it]
            }.toSortedSet().toList()
        }
    }


    fun expectedWords(reference: String): List<String> =
        wordUsedRepo.expectedWords(reference)

    fun usedWords(reference: String): List<TurnDto> {
        val game = gameRepo.findByReference(reference)
        return wordUsedRepo.findAllByGameReferenceOrderByMovedAtAsc(reference)
            .map {
                TurnDto(
                    it.word.word.uppercase().toList(),
                    it.player,
                    game.computerPlaysAs.contains(it.player)
                )
            }
    }

    fun lastWordUsed(reference: String): String =
        wordUsedRepo.lastWord(reference).word
}