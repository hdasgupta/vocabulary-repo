package play.vocab.playwithvocab.guess_the_word.entities

import play.vocab.playwithvocab.guess_the_word.common.GuessType
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "gtw_guess_chart")
class GTWGuessChart(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guess_chart_gen")
    @SequenceGenerator(name = "guess_chart_gen", sequenceName = "guess_chart_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NotNull
    @ManyToOne(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "game_id", nullable = false)
    var game: GTWGame,

    @NotNull
    var char:Char,

    @NotNull
    @Enumerated(EnumType.STRING)
    var type: GuessType = GuessType.NoIdea

) {
    constructor():this(game=GTWGame(), char = '-')
}