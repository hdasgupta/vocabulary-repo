package play.vocab.playwithvocab.change_one_letter.entiries

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import org.hibernate.Hibernate
import org.springframework.data.jpa.repository.Temporal
import org.springframework.format.annotation.DateTimeFormat
import play.vocab.playwithvocab.common.entiries.Word
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "game_words")
data class WordUsedInGame(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "used_gen")
    @SequenceGenerator(name = "used_gen", sequenceName = "used_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "game_id", nullable = false)
    var game: COLGame,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "word_id", nullable = false)
    var word: Word,

    @NotNull
    @Min(0)
    var player: Int,

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    var movedAt: Date = Date()

) {
    constructor() : this(game = COLGame(), word = Word(), player = 0) {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as WordUsedInGame

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(game = $game , word = $word , player = $player )"
    }

}