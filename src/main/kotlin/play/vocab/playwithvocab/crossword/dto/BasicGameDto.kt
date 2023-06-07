package play.vocab.playwithvocab.crossword.dto

data class BasicGameDto(
    var id: String,
    var startedAt: String,
    var wordCount: Int,
    var cyclicEnabled: Boolean,
    var adjacentEnabled: Boolean,
    var closed: Boolean
)
