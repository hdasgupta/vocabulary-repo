package play.vocab.playwithvocab.crossword.mappers

import org.springframework.stereotype.Component
import play.vocab.playwithvocab.common.repos.WordRepo
import play.vocab.playwithvocab.crossword.dto.*
import play.vocab.playwithvocab.crossword.entities.CWGames
import play.vocab.playwithvocab.crossword.plugins.findAllLTRWordByBoardReference
import play.vocab.playwithvocab.crossword.plugins.findAllTTBWordByBoardReference
import play.vocab.playwithvocab.crossword.repos.*

@Component
class CWGameEntityToDtoMapper(
    private val gameRepo: CWGameRepo,
    private val locationRepo: CWLocationRepo,
    private val inputRepo: CWInputRepo,
    private val cwWordRepo: CWWordRepo,
) {
    fun getGameDto(
        game: CWGames,
        locations: List<LocationDto>
    ) = GameDto(
        game.reference,
        WordsDto(
            cwWordRepo.findAllLTRWordByBoardReference(game.board.reference)
                .map {
                    IndexedDesc(
                        it.index,
                        it.wordDesc.description
                    )
                },
            cwWordRepo.findAllTTBWordByBoardReference(game.board.reference)
                .map {
                    IndexedDesc(
                        it.index,
                        it.wordDesc.description
                    )
                }
        ),
        locations,
        SizeDto(
            game.board.size.rows,
            game.board.size.cols
        ),
        canSubmit(game.reference),
        game.isCorrect
    )


    fun getGame(game:CWGames): GameDto {
        val locations = locationRepo.findAllByBoardReference(game.board.reference)
            .map {
                LocationDto(
                    it.index,
                    it.row,
                    it.col
                )
            }
        return getGameDto(game, locations)
    }

    fun canSubmit(reference:String): Boolean {
        val game = gameRepo.findByReference(reference)
        val inputs = inputRepo.findAllByGameReference(game.reference)
            .associateBy {
                it.location.id!!
            }
        return locationRepo.findAllByBoardReference(game.board.reference)
            .all {
                inputs[it.id] != null
            }
    }

}