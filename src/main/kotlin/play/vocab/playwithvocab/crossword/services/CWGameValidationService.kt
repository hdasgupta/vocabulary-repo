package play.vocab.playwithvocab.crossword.services

import org.springframework.stereotype.Service
import play.vocab.playwithvocab.crossword.dto.GameDto
import play.vocab.playwithvocab.crossword.enums.WordDirection
import play.vocab.playwithvocab.crossword.mappers.CWGameEntityToDtoMapper
import play.vocab.playwithvocab.crossword.repos.*

@Service
class CWGameValidationService(
    private val gameRepo: CWGameRepo,
    private val locationRepo: CWLocationRepo,
    private val inputRepo: CWInputRepo,
    private val cwWordRepo: CWWordRepo,
    private val gameMapper: CWGameEntityToDtoMapper
) {


    fun verify(reference: String): GameDto {
        val game = gameRepo.findByReference(reference)
        val locationByIndex = locationRepo.findAllByBoardReference(game.board.reference)
            .filter {
                it.index != null
            }.associateBy {
                it.index!!
            }
        val chars: List<MutableList<Char?>> = (1..game.board.size.rows).map {
            (1..game.board.size.cols).map {
                null
            }.toMutableList()
        }
        inputRepo.findAllByGameReference(game.reference)
            .forEach {
                chars[it.location.row][it.location.col] = it.input
            }
        val valid = cwWordRepo.findAllByBoardReference(game.board.reference).all {
            val initLoc = locationByIndex[it.index]!!
            it.wordDesc.word.word.mapIndexed() {
                    index, c -> Pair(index, c)
            }.associate {
                    p->p
            }.all {
                    entry ->
                when(it.direction) {
                    WordDirection.LeftToRight -> chars[initLoc.row][initLoc.col + entry.key] == entry.value
                    WordDirection.TopToBottom -> chars[initLoc.row + entry.key][initLoc.col] == entry.value
                }
            }
        }
        game.isCorrect = valid
        game.closed = true

        return gameMapper.getGame(
            gameRepo.save(game)
        )
    }



}