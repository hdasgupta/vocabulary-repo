package play.vocab.playwithvocab.common.repos

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.common.entiries.Word

@Repository
interface WordRepo : CrudRepository<Word, Long> {
    fun findByWordIgnoreCase(word: String): Word?


    fun existsByWordIgnoreCase(word: String): Boolean

    @Query("select r.validChange from Relation r where r.word.id = :wordId")
    fun findByValidList(wordId: Long): List<Word>

    @Query("select count(r.validChange)=0 from Relation r where r.word.id = :wordId")
    fun hasValidList(wordId: Long): Boolean

    fun findAllBySizeEqualsAndWordNotIn(size:Int, words:List<String>):List<Word>

    fun countBySizeEquals(
        size: Int
    ): Int

    fun findAllBySizeEquals(
        size: Int,
        page: Pageable
    ): List<Word>

    fun findAllByValidListIsNotEmptyAndSizeGreaterThanEqualAndSizeLessThanEqual(
        minExclusive: Int = 4,
        maxExclusive: Int = 4,
        page: Pageable
    ): List<Word>

    fun countByValidListIsNotEmptyAndSizeGreaterThanEqualAndSizeLessThanEqual(
        minExclusive: Int = 4,
        maxExclusive: Int = 4
    ):Int
}