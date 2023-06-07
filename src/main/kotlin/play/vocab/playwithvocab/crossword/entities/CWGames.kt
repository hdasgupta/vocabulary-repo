package play.vocab.playwithvocab.crossword.entities

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import org.hibernate.validator.constraints.UniqueElements
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.lang.Nullable
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.common.plugins.REFERENCE_STRING_LENGTH
import play.vocab.playwithvocab.common.plugins.randomString
import java.util.Date
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "cw_game")
data class CWGames(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cw_game_gen")
    @SequenceGenerator(name = "cw_game_gen", sequenceName = "cw_game_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NaturalId
    var reference: String = REFERENCE_STRING_LENGTH.randomString(),

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "board_id")
    var board: CWBoards,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    var startedAt: Date = Date(),

    @Nullable
    var isCorrect: Boolean? = null,

    var closed:Boolean = isCorrect != null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CWGames

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
