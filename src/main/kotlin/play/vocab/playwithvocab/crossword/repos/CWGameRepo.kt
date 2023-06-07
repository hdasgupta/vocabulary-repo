package play.vocab.playwithvocab.crossword.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.crossword.entities.CWBoards
import play.vocab.playwithvocab.crossword.entities.CWGames

@Repository
interface CWGameRepo:CrudRepository<CWGames, Long> {
    fun findByReference(reference:String):CWGames
}