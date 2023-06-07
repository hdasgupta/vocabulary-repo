package play.vocab.playwithvocab.guess_the_word.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.guess_the_word.entities.GTWGame
import play.vocab.playwithvocab.guess_the_word.entities.GTWGuessChart

@Repository
interface GTWGuessChartRepo: CrudRepository<GTWGuessChart, Long> {
    fun findAllByGameReference(reference:String):List<GTWGuessChart>
    fun findByGameReferenceAndCharEquals(reference:String, char:Char):GTWGuessChart
}