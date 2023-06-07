package play.vocab.playwithvocab.crossword.entities

import org.hibernate.Hibernate
import org.hibernate.annotations.NaturalId
import org.hibernate.validator.constraints.UniqueElements
import org.springframework.data.jpa.repository.Temporal
import org.springframework.format.annotation.DateTimeFormat
import play.vocab.playwithvocab.common.plugins.REFERENCE_STRING_LENGTH
import play.vocab.playwithvocab.common.plugins.randomString
import play.vocab.playwithvocab.crossword.enums.JobStatus
import java.util.Date
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "cw_board_job")
data class CWBoardCreationJob(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cw_board_gen")
    @SequenceGenerator(name = "cw_board_gen", sequenceName = "cw_board_seq")
    @Column(name = "id")
    open var id: Long? = null,

    @NaturalId
    var reference: String = REFERENCE_STRING_LENGTH.randomString(),

    @NotNull
    @OneToMany(
        cascade = [CascadeType.MERGE, CascadeType.REMOVE,
            CascadeType.REFRESH, CascadeType.DETACH],
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "cw_word_by_job",
        joinColumns = [JoinColumn(name = "desc_id")]
    )
    var words: List<CWWordDescriptions>,

    var options: Int? = null,

    @Enumerated(EnumType.STRING)
    var status: JobStatus,

    var message: String,

    @Temporal
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    var createdAt: Date = Date(),

    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: Date = Date(),
) {
    fun updated():Unit {
        updatedAt = Date()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CWBoardCreationJob

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
