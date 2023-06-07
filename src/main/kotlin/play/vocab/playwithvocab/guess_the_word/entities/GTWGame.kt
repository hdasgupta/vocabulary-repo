package play.vocab.playwithvocab.guess_the_word.entities

import javax.validation.constraints.NotNull
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
import javax.validation.constraints.PositiveOrZero
import kotlin.collections.ArrayList

@Entity
@Table
data class GTWGame(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gtw_game_gen")
    @SequenceGenerator(name = "gtw_game_gen", sequenceName = "gtw_game_seq")
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
    var wordToGuess: Word,

    @NotNull
    @PositiveOrZero
    var numOfAttempts: Int = 0,

    @PositiveOrZero
    var point: Int = 100,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    var startedAt: Date = Date(),

    @ManyToMany(
        cascade = [CascadeType.PERSIST, CascadeType.REFRESH],
        fetch = FetchType.EAGER,
    )
    @JoinColumn(
        table = "gtw_relation",
        name = "word_id",
        referencedColumnName="game_id"
    )
    var guessedWords: List<Word> = listOf()

) {
    var closed = guessedWords.contains(wordToGuess) || point==0
    constructor() : this(wordToGuess = Word()) {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as GTWGame

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(initial = $wordToGuess , attempts = $numOfAttempts , point = $point )"
    }

}
