package play.vocab.playwithvocab.guess_the_word.services

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import play.vocab.playwithvocab.common.repos.WordRepo
import play.vocab.playwithvocab.guess_the_word.common.GuessType
import play.vocab.playwithvocab.guess_the_word.dto.BasicGameDto
import play.vocab.playwithvocab.guess_the_word.dto.GameDto
import play.vocab.playwithvocab.guess_the_word.dto.MatchDto
import play.vocab.playwithvocab.guess_the_word.entities.GTWGame
import play.vocab.playwithvocab.guess_the_word.entities.GTWGuessChart
import play.vocab.playwithvocab.guess_the_word.entities.GTWRelation
import play.vocab.playwithvocab.guess_the_word.repos.GTWGameRepo
import play.vocab.playwithvocab.guess_the_word.repos.GTWGuessChartRepo
import play.vocab.playwithvocab.guess_the_word.repos.GTWRelationRepo
import java.text.DateFormat
import java.text.SimpleDateFormat
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import kotlin.jvm.optionals.getOrNull

@Service
class GTWGameService (
    private var gameRepo: GTWGameRepo,
    private var wordRepo: WordRepo,
    private var relationRepo: GTWRelationRepo,
    private var guessChartRepo: GTWGuessChartRepo
){
    private val dateFormatter:DateFormat =
        SimpleDateFormat.getDateTimeInstance()

    fun getAll(): List<BasicGameDto> =
        gameRepo.findAllByOrderByStartedAt()
            .map {
                BasicGameDto(
                    it.reference,
                    dateFormatter.format(it.startedAt),
                    it.wordToGuess.size,
                    relationRepo.findAllByGameReferenceOrderByMovedAt(it.reference)
                        .any {
                                relation->
                            relation.word.word.equals(it.wordToGuess.word, true)
                        }
                )
            }

    fun get(reference:String): GameDto =
        getGameDto(
            gameRepo.findByReference(reference),
            guessChartRepo.findAllByGameReference(reference)
        )

    fun new(@Valid @Min(3) @Max(6) size: Int): GameDto {
        val game = gameRepo.save(
            GTWGame(
                wordToGuess = wordRepo.findAllBySizeEquals(
                    size,
                    Pageable.ofSize(1).withPage(
                        (0 until wordRepo.countBySizeEquals(size))
                            .random()
                    )
                )[0]
            )
        )
        val chart = guessChartRepo.saveAll(
            ('A' .. 'Z').map {
                GTWGuessChart(
                    game = game,
                    char = it
                )
            }
        )
        return getGameDto(
            game,
            chart
        )
    }


    fun getGameDto(it: GTWGame, chart:Iterable<GTWGuessChart>) = GameDto(
        it.reference,
        it.numOfAttempts,
        it.wordToGuess.size,
        it.point,
        relationRepo.findAllByGameReferenceOrderByMovedAt(it.reference)
            .map { word ->
                MatchDto(
                    word.word.word.uppercase(),
                    word.word.word.count {
                        chr ->
                        it.wordToGuess.word.contains(chr, true)
                    }
                )

            }.reversed(),
        chart.associate {
            Pair(it.char, it.type)
        },
        relationRepo.findAllByGameReferenceOrderByMovedAt(it.reference)
            .any {
                word->
                    word.word.word.equals(it.wordToGuess.word, true)
            }
    )

    @OptIn(ExperimentalStdlibApi::class)
    fun addWord(reference: String, word: String): GameDto? =
        gameRepo.findByReference(reference)
            .let {
                if(!it.closed) {
                    val w = wordRepo.findByWordIgnoreCase(word)
                    it.numOfAttempts++;
                    if(it.numOfAttempts>it.wordToGuess.size)
                        it.point--
                    val rel = GTWRelation(word = w!!, game = it)
                    relationRepo.save(rel)
                    it.closed = relationRepo.findAllByGameReferenceOrderByMovedAt(reference)
                        .any {
                                relation->
                            relation.word.word.equals(it.wordToGuess.word, true)
                        }

                    getGameDto(
                        gameRepo.save(it),
                        guessChartRepo.findAllByGameReference(reference)
                    )
                } else {
                    null
                }
            }

    fun changeGuessType(reference: String, char:Char, type:GuessType):Map<Char, GuessType> {
        val guess = guessChartRepo.findByGameReferenceAndCharEquals(reference, char)
        guess.type = type
        guessChartRepo.save(guess)
        return guessChartRepo.findAllByGameReference(reference)
            .associate {
                Pair(it.char, it.type)
            }
    }

    fun isValidWord(reference: String, word: String): Boolean =
        if(relationRepo.existsByGameReferenceAndWordWord(reference, word)) {
            false;
        } else {
            wordRepo.existsByWordIgnoreCase(word)
        }


}