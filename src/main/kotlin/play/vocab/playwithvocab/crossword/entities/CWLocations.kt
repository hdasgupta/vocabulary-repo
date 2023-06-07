package play.vocab.playwithvocab.crossword.entities

import org.hibernate.Hibernate
import org.springframework.lang.Nullable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "cw_location")
data class CWLocations(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cw_word_gen")
    @SequenceGenerator(name = "cw_word_gen", sequenceName = "cw_word_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "board_id")
    var board: CWBoards,

    @Nullable
    val index: Int? = null,

    @Nullable
    val row: Int,

    @Nullable
    val col: Int

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CWLocations

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
