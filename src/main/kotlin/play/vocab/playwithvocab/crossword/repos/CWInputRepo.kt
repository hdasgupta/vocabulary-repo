package play.vocab.playwithvocab.crossword.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.crossword.entities.CWBoards
import play.vocab.playwithvocab.crossword.entities.CWInputs

@Repository
interface CWInputRepo:CrudRepository<CWInputs, Long> {
    fun findAllByGameReference(gameReference:String): List<CWInputs>
    fun findAllByGameReferenceAndLocationId(gameReference:String, locationId: Long): CWInputs?
}