package play.vocab.playwithvocab.common.repos

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import play.vocab.playwithvocab.common.entiries.Relation

@Repository
interface RelationRepo : CrudRepository<Relation, Long> {

}