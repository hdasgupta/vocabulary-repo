package play.vocab.playwithvocab.change_one_letter.entiries

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import org.hibernate.validator.constraints.UniqueElements
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.common.plugins.REFERENCE_STRING_LENGTH
import play.vocab.playwithvocab.common.plugins.randomString

import javax.persistence.*

@Entity
@Table
data class COLGame(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "col_game_gen")
    @SequenceGenerator(name = "col_game_gen", sequenceName = "col_game_seq")
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
    @JoinColumn(name = "word_id")
    var initial: Word,

    @NotNull
    @Min(2)
    var playerCount: Int = 2,

    @Min(0)
    var winner: Int? = null,

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    var computerPlaysAs: Set<Int> = setOf(0),

    @Min(0)
    var playerTurn: Int = computerPlaysAs.elementAt(0),

    @Min(-1)
    @NotNull
    var lastManualMoveIndex: Int = -1,

    @ManyToMany(
        cascade = [CascadeType.PERSIST, CascadeType.REFRESH],
        fetch = FetchType.EAGER,
    )
    @JoinColumn(
        table = "word_used_in_game",
        name = "word_id",
        referencedColumnName="game_id",
    )
    var usedWords: List<Word> = listOf()
) {
    constructor() : this(initial = Word()) {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as COLGame

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(initial = $initial , playerCount = $playerCount , computerPlaysAs = $computerPlaysAs )"
    }

}
