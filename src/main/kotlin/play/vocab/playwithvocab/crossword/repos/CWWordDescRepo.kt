package play.vocab.playwithvocab.crossword.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.crossword.entities.CWWordDescriptions

@Repository
interface CWWordDescRepo:CrudRepository<CWWordDescriptions, Long> {
}