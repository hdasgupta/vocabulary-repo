package play.vocab.playwithvocab.crossword.entities

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import org.hibernate.validator.constraints.UniqueElements
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.common.plugins.REFERENCE_STRING_LENGTH
import play.vocab.playwithvocab.common.plugins.randomString
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "cw_board")
data class CWBoards (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cw_board_gen")
    @SequenceGenerator(name = "cw_board_gen", sequenceName = "cw_board_seq")
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
    @JoinColumn(
        name = "job_id",
    )
    var job: CWBoardCreationJob,

    @Embedded
    var size: CWSize,

    @Embedded
    var option: CWOptions,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CWBoards

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
