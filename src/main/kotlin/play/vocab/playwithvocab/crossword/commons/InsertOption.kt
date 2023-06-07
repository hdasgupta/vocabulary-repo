package play.vocab.playwithvocab.crossword.commons

import play.vocab.playwithvocab.crossword.enums.WordDirection

data class InsertOption(
    val word: String,
    val description: String,
    val direction: WordDirection,
    val enableCyclic: Boolean,
    val enableAdjacent: Boolean
)
