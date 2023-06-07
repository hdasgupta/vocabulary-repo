package play.vocab.playwithvocab.crossword.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import play.vocab.playwithvocab.common.repos.WordRepo
import play.vocab.playwithvocab.crossword.builders.BoardBuilder
import play.vocab.playwithvocab.crossword.dto.*
import play.vocab.playwithvocab.crossword.entities.*
import play.vocab.playwithvocab.crossword.enums.WordDirection
import play.vocab.playwithvocab.crossword.dto.GameDto
import play.vocab.playwithvocab.crossword.mappers.CWBoardEntityToDtoMapper
import play.vocab.playwithvocab.crossword.mappers.CWGameEntityToDtoMapper
import play.vocab.playwithvocab.crossword.repos.*

@Service
class CWGameService(
    private val gameRepo: CWGameRepo,
    private val boardRepo: CWBoardRepo,
    private val locationRepo: CWLocationRepo,
    private val inputRepo: CWInputRepo,
    private val cwWordRepo: CWWordRepo,
    private val gameMapper: CWGameEntityToDtoMapper
) {

    fun getAll(): List<BasicGameDto> =
        gameRepo.findAll()
            .map {
                BasicGameDto(
                    it.reference,
                    it.startedAt.toString(),
                    cwWordRepo.countByBoardReference(it.board.reference),
                    it.board.option.cyclicEnabled,
                    it.board.option.adjacentEnabled,
                    it.closed
                )
            }

    fun new():GameDto =
        gameMapper.getGame(
            gameRepo.save(
                CWGames(
                    board = boardRepo.findAll(
                        Pageable.ofSize(1)
                            .withPage(
                                (0 until boardRepo.count().toInt()).random()
                            )
                    ).elementAt(0)
                )
            )
        )

    fun get(reference: String): GameDto =
        gameMapper.getGame(
            gameRepo.findByReference(reference)
        )

    fun put(reference:String, row: Int, column:Int, input: Char):GameDto {
        val game = gameRepo.findByReference(reference)
        val location = locationRepo.findByBoardReferenceAndRowAndCol(game.board.reference, row, column)
        var inp = inputRepo.findAllByGameReferenceAndLocationId(reference, location.id!!)
        if(inp == null) {
            inp = CWInputs(
                location = location,
                game = game,
                input = input
            )
        } else {
            inp.input = input
        }
        inputRepo.save(inp)
        return gameMapper.getGame(game)
    }


}