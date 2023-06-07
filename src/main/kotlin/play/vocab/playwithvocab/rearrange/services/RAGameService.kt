package play.vocab.playwithvocab.rearrange.services

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import play.vocab.playwithvocab.common.repos.WordRepo
import play.vocab.playwithvocab.rearrange.dto.BasicGameDto
import play.vocab.playwithvocab.rearrange.dto.GameDto
import play.vocab.playwithvocab.rearrange.entiries.RAGame
import play.vocab.playwithvocab.rearrange.entiries.RARelation
import play.vocab.playwithvocab.rearrange.entiries.Rearrange
import play.vocab.playwithvocab.rearrange.plugins.pow
import play.vocab.playwithvocab.rearrange.repos.RAGameRepo
import play.vocab.playwithvocab.rearrange.repos.RARelationRepo
import play.vocab.playwithvocab.rearrange.repos.RearrangeRepo

@Service
class RAGameService(
    private var gameRepo: RAGameRepo,
    private var relationRepo: RARelationRepo,
    private var rearrangeRepo: RearrangeRepo,
    private var wordRepo: WordRepo
) {

    fun getAll():List<BasicGameDto> =
        gameRepo.findAll()
            .map {
                BasicGameDto(
                    it.reference,
                    it.rearrange.usedChars.map { c->c.uppercaseChar() }.toSet().toList(),
                    it.closed
                )
            }

    fun get(reference:String): GameDto =
        gameRepo.findByReference(reference)
            .let {
                getGameDto(it)
            }


    fun new(): GameDto =
        getGameDto(
            gameRepo.save(
                RAGame(
                    rearrange = randomRearrange()
                )
            )
        )

    fun validate(reference:String, word: String):Boolean =
        !relationRepo.existsByGameReferenceAndWordWordIgnoreCase(reference, word) &&
                wordRepo.existsByWordIgnoreCase(word)

    fun add(reference:String, word:String): GameDto =
        gameRepo.findByReference(reference)
            .let {
                val w = wordRepo.findByWordIgnoreCase(word)
                relationRepo.save(
                    RARelation(
                        word = w!!,
                        game = it
                    )
                )
                it
            }.let {
                getGameDto(it)
            }

    fun close(reference:String):Boolean =
        gameRepo.findByReference(reference)
            .let {
                if(it.closable && !it.closed) {
                    it.closed = true
                    gameRepo.save(it)
                } else {
                    null
                }
            } != null

    fun getGameDto(game: RAGame): GameDto {
        val guesses = relationRepo.findAllByGameReference(game.reference)
            .map { relation ->
                relation.word.word.uppercase()
            }
        game.closable = guesses.size >= game.rearrange.usedChars.toSet().size
        game.points = if(guesses.size < game.rearrange.usedChars.toSet().size) {
            0
        } else {
            2.pow(guesses.size-game.rearrange.usedChars.toSet().size)*100
        }

        gameRepo.save(game)

        return GameDto(
            game.reference,
            game.rearrange.usedChars
                .map {
                     it.uppercaseChar()
                }
                .toSet()
                .toList(),
            guesses,
            game.closable,
            game.points
        )
    }

    fun randomRearrange():Rearrange =
        rearrangeRepo.findAllByInterestingIsTrue(
            Pageable
                .ofSize(1)
                .withPage(
                    (0 until rearrangeRepo.countByInterestingIsTrue())
                        .random()
                )
        ).first()

}