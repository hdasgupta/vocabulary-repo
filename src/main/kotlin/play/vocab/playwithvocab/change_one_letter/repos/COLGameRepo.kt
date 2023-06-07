package play.vocab.playwithvocab.change_one_letter.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.change_one_letter.entiries.COLGame

@Repository
interface COLGameRepo : CrudRepository<COLGame, Long> {
    fun findByReference(reference: String):COLGame
}