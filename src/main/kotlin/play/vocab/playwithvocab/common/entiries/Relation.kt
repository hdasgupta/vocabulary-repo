package play.vocab.playwithvocab.common.entiries

import javax.validation.constraints.NotNull
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table
data class Relation(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "relation_gen")
    @SequenceGenerator(name = "relation_gen", sequenceName = "relation_seq")
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
    @JoinColumn(name = "valid_id")
    val validChange: Word
) {
    constructor() : this(word = Word(), validChange = Word()) {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Relation

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(word = $word , validChange = $validChange )"
    }

}