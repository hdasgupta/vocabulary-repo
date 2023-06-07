package play.vocab.playwithvocab.crossword.entities

import org.hibernate.Hibernate
import play.vocab.playwithvocab.crossword.enums.WordDirection
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "cw_word_by_board")
data class CWWordByBoard(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cw_word_gen")
    @SequenceGenerator(name = "cw_word_gen", sequenceName = "cw_word_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER,
        targetEntity = CWBoards::class
    )
    @JoinColumn(name = "board_id")
    var board: CWBoards,

    @NotNull
    var index: Int,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER,
        targetEntity = CWWordDescriptions::class
    )
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    var wordDesc: CWWordDescriptions,

    var direction:WordDirection
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CWWordByBoard

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
