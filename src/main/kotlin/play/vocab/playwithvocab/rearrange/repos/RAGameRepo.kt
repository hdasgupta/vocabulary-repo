package play.vocab.playwithvocab.rearrange.repos

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.rearrange.entiries.RAGame
import play.vocab.playwithvocab.rearrange.entiries.Rearrange


@Repository
interface RAGameRepo : CrudRepository<RAGame, Long> {
    fun findByReference(reference:String):RAGame

}