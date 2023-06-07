package play.vocab.playwithvocab.crossword.repos

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.crossword.entities.CWBoards

@Repository
interface CWBoardRepo:CrudRepository<CWBoards, Long> {

    fun findAll(pageable: Pageable):Page<CWBoards>
    fun findAllByJobReference(jobReference: String, pageable: Pageable):Page<CWBoards>
    fun findByReference(reference: String): CWBoards

}