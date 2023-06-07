package play.vocab.playwithvocab.common.entiries

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import org.hibernate.Hibernate
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@Table
data class Word(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "word_gen")
    @SequenceGenerator(name = "word_gen", sequenceName = "word_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    @NotBlank
    var word: String,

    @Min(3)
    var size: Int = word.length,

    @ManyToMany(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "relation",
        joinColumns = [JoinColumn(name = "word_id")],
        inverseJoinColumns = [JoinColumn(name = "valid_id")]
    )
    var validList: List<Word> = listOf()

) {

    constructor() : this(word = "") {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Word

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(word = $word )"
    }

}

