package play.vocab.playwithvocab.crossword.repos

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.crossword.entities.CWBoardCreationJob
import play.vocab.playwithvocab.crossword.entities.CWWordDescriptions

@Repository
interface CWJobRepo:CrudRepository<CWBoardCreationJob, Long> {
    fun findByReference(reference:String):CWBoardCreationJob
    fun findAll(pageable: Pageable):Page<CWBoardCreationJob>
}