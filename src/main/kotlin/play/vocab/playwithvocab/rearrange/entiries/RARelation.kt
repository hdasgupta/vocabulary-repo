package play.vocab.playwithvocab.rearrange.entiries

import org.springframework.data.jpa.repository.Temporal
import org.springframework.format.annotation.DateTimeFormat
import play.vocab.playwithvocab.common.entiries.Word
import java.util.Date
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name="ra_relation")
class RARelation(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ra_relation_gen")
    @SequenceGenerator(name = "gra_relation_gen", sequenceName = "ra_relation_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "word_id")
    val word: Word,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "game_id")
    val game: RAGame,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    val movedAt:Date = Date()
) {
}