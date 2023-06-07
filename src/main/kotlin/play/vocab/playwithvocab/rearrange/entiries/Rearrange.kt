package play.vocab.playwithvocab.rearrange.entiries

import org.hibernate.Hibernate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import play.vocab.playwithvocab.common.entiries.Word
import javax.persistence.*

@Entity
@Table
data class Rearrange(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rearrange_gen")
    @SequenceGenerator(name = "rearrange_gen", sequenceName = "rearrange_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    var interesting: Boolean = false,

    @NotNull
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    var usedChars: List<Char> = listOf(),

    @NotNull
    @NotEmpty
    @OneToMany(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "rearrange_words",
        joinColumns = [JoinColumn(name = "rearrange_id")],
        inverseJoinColumns = [JoinColumn(name = "word_id")]
    )
    var words: Set<Word> = setOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Rearrange

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , interesting = $interesting , usedChars = $usedChars )"
    }

}