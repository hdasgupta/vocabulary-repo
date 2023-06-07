package play.vocab.playwithvocab.crossword.entities

import javax.persistence.Embeddable

@Embeddable
data class CWOptions(
    var cyclicEnabled: Boolean,
    var adjacentEnabled: Boolean,
)
