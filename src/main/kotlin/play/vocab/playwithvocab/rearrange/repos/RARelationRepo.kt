package play.vocab.playwithvocab.rearrange.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.rearrange.entiries.RARelation


@Repository
interface RARelationRepo : CrudRepository<RARelation, Long> {

    fun findAllByGameReference(reference:String): List<RARelation>

    fun existsByGameReferenceAndWordWordIgnoreCase(reference:String, word: String): Boolean
}