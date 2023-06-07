package play.vocab.playwithvocab.crossword.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.crossword.entities.CWLocations

@Repository
interface CWLocationRepo:CrudRepository<CWLocations, Long> {
    fun findAllByBoardReference(reference:String):List<CWLocations>
    fun findByBoardReferenceAndRowAndCol(reference:String, row:Int, col:Int):CWLocations
}