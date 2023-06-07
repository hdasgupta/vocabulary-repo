package play.vocab.playwithvocab.rearrange.entiries

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import org.hibernate.validator.constraints.UniqueElements
import org.springframework.data.jpa.repository.Temporal
import org.springframework.format.annotation.DateTimeFormat
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.common.plugins.REFERENCE_STRING_LENGTH
import play.vocab.playwithvocab.common.plugins.randomString
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table
data class RAGame(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ra_game_gen")
    @SequenceGenerator(name = "ra_game_gen", sequenceName = "ra_game_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NaturalId
    val reference:String = REFERENCE_STRING_LENGTH.randomString(),

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "rearrange_id")
    var rearrange: Rearrange,


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    var startedAt: Date = Date(),

    var closable: Boolean = false,

    var points: Int = 0,

    var closed:Boolean = false,
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as RAGame

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , rearrange = $rearrange , startedAt = $startedAt )"
    }
}