package play.vocab.playwithvocab.crossword.entities

import javax.persistence.Embeddable

@Embeddable
data class CWSize(
    val rows: Int,
    val cols: Int
)
