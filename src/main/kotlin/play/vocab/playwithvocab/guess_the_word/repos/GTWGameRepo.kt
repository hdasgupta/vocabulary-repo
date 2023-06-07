package play.vocab.playwithvocab.guess_the_word.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.guess_the_word.entities.GTWGame

@Repository
interface GTWGameRepo: CrudRepository<GTWGame, Long> {
    fun findAllByOrderByStartedAt():List<GTWGame>;
    fun countByOrderByStartedAt():Int;
    fun findByReference(reference:String):GTWGame
}