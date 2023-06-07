package play.vocab.playwithvocab.crossword.mappers

import org.springframework.stereotype.Component
import play.vocab.playwithvocab.crossword.dto.*
import play.vocab.playwithvocab.crossword.entities.CWBoards
import play.vocab.playwithvocab.crossword.entities.CWLocations
import play.vocab.playwithvocab.crossword.enums.WordDirection
import play.vocab.playwithvocab.crossword.repos.CWWordRepo

@Component
class CWBoardEntityToDtoMapper(
    private val cwWordRepo: CWWordRepo,
) {
    fun getDetailBoard(b: CWBoards, locations:List<CWLocations>): BoardDto {
        val words = cwWordRepo.findAllByBoardReference(b.reference)
        return BoardDto(
            words
                .filter {
                    it.direction == WordDirection.LeftToRight
                }
                .map {
                    IndexedDesc(
                        it.index,
                        it.wordDesc.description
                    )
                },
            words
                .filter {
                    it.direction == WordDirection.TopToBottom
                }
                .map {
                    IndexedDesc(
                        it.index,
                        it.wordDesc.description
                    )
                },
            SizeDto(
                b.size.rows,
                b.size.cols
            ),
            locations.map {
                LocationDto(
                    it.index,
                    it.row,
                    it.col
                )
            },
            b.option.cyclicEnabled,
            b.option.adjacentEnabled
        )
    }

    fun getBoard(b: CWBoards, locations:List<CWLocations>): BasicBoardDto {
        val locationByIndex = locations
            .filter {
                it.index != null
            }.associateBy {
                it.index!!
            }
        val chars: List<MutableList<Char?>> = (1..b.size.rows).map {
            (1..b.size.cols).map {
                null
            }.toMutableList()
        }
        cwWordRepo.findAllByBoardReference(b.reference).forEach {
            val initLoc = locationByIndex[it.index]!!
            it.wordDesc.word.word.forEachIndexed {
                    index, char ->
                when(it.direction) {
                    WordDirection.LeftToRight -> chars[initLoc.row][initLoc.col + index] = char.uppercaseChar()
                    WordDirection.TopToBottom -> chars[initLoc.row + index][initLoc.col] = char.uppercaseChar()
                }
            }

        }
        return BasicBoardDto(
            b.reference,
            locations.map {
                LocationInputDto(
                    it.index,
                    it.row,
                    it.col,
                    chars[it.row][it.col]
                )
            },
            SizeDto(
                b.size.rows,
                b.size.cols
            )
        )
    }

}