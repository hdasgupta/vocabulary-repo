package play.vocab.playwithvocab.crossword.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import play.vocab.playwithvocab.crossword.dto.BasicBoardDto
import play.vocab.playwithvocab.crossword.dto.BoardDto
import play.vocab.playwithvocab.crossword.mappers.CWBoardEntityToDtoMapper
import play.vocab.playwithvocab.crossword.repos.CWBoardRepo
import play.vocab.playwithvocab.crossword.repos.CWLocationRepo

@Service
class CWBoardService(
    private val boardRepo: CWBoardRepo,
    private val locationRepo: CWLocationRepo,
    private val boardMapper: CWBoardEntityToDtoMapper,

) {

    fun getAllBoard(page: Int, size: Int): Page<BasicBoardDto> =
        boardRepo.findAll(Pageable.ofSize(size).withPage(page))
            .map {
                boardMapper.getBoard(
                    it,
                    locationRepo.findAllByBoardReference(it.reference)
                )
            }

    fun getAllBoardByJob(jobReference:String, page: Int): Page<BasicBoardDto> =
        boardRepo.findAllByJobReference(
            jobReference,
            Pageable.ofSize(1).withPage(page))
                .map {
                    boardMapper.getBoard(
                        it,
                        locationRepo.findAllByBoardReference(it.reference)
                    )
                }

    fun getBoard(reference:String): BoardDto =
        boardMapper.getDetailBoard(
            boardRepo.findByReference(reference),
            locationRepo.findAllByBoardReference(reference)
        )


}