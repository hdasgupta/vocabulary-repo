package play.vocab.playwithvocab.guess_the_word.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.guess_the_word.entities.GTWRelation

@Repository
interface GTWRelationRepo: CrudRepository<GTWRelation, Long> {
    fun findAllByGameReferenceOrderByMovedAt(reference:String):List<GTWRelation>
    fun existsByGameReferenceAndWordWord(reference:String, word:String):Boolean
}