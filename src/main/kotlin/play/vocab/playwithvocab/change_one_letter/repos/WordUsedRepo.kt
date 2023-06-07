package play.vocab.playwithvocab.change_one_letter.repos

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.change_one_letter.entiries.WordUsedInGame
import play.vocab.playwithvocab.common.entiries.Word

@Repository
interface WordUsedRepo : CrudRepository<WordUsedInGame, Long> {
    @Query("select vl.word from WordUsedInGame wu inner join wu.word w inner join wu.game g inner join w.validList vl where g.reference=:reference and wu.movedAt in (select max(wui.movedAt) from WordUsedInGame wui inner join wui.game gm where gm.reference=:reference) and vl.id not in (select wo.id from WordUsedInGame u inner join u.word wo inner join u.game ga where ga.reference=:reference)")
    fun expectedWords(reference: String): List<String>

    @Query("Select w from WordUsedInGame wu inner join wu.game g inner join wu.word w where g.reference=:reference and wu.movedAt in (select max(wui.movedAt) from WordUsedInGame wui inner join wui.game gm where gm.reference=:reference)")
    fun lastWord(reference: String): Word

//    @Query("Select w.word from WordUsedInGame wu inner join wu.game g inner join wu.word w where g.id=:gameId order by wu.movedAt asc")
//    fun usedWords(gameId: Long): List<String>

    //fun findAllWordUsedInGameWordByGameIdOrderByMovedAtAsc(gameId: Long): List<Word>

    fun findAllByGameReferenceOrderByMovedAtAsc(reference: String): List<WordUsedInGame>

}

