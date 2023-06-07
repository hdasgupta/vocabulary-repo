package play.vocab.playwithvocab.rearrange.repos

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.rearrange.entiries.Rearrange

@Repository
interface RearrangeRepo : CrudRepository<Rearrange, Long> {

    @Query("select w from Rearrange r inner join r.words w where w.word=:word")
    fun rearrangedWords(word: String): List<Word>

    fun countByInterestingIsTrue(): Int

    fun findAllByInterestingIsTrue(pageable:Pageable): Page<Rearrange>


}