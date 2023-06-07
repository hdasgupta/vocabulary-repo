package play.vocab.playwithvocab.crossword.entities

import org.hibernate.Hibernate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "cw_input")
data class CWInputs(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cw_input_gen")
    @SequenceGenerator(name = "cw_input_gen", sequenceName = "cw_input_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "location_id")
    var location: CWLocations,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "game_id")
    var game: CWGames,

    var input: Char
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CWInputs

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
