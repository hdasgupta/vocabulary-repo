package play.vocab.playwithvocab.crossword.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.crossword.entities.CWWordByBoard
import play.vocab.playwithvocab.crossword.enums.WordDirection

@Repository
interface CWWordRepo:CrudRepository<CWWordByBoard, Long> {
    fun findAllByBoardReference(boardReference: String): List<CWWordByBoard>
    fun countByBoardReference(boardReference: String): Int
    fun findAllByBoardReferenceAndDirection(boardReference: String, direction:WordDirection): List<CWWordByBoard>
}