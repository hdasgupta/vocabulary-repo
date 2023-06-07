package play.vocab.playwithvocab.crossword.dto

data class BoardDetailsDto(
    var wordByDesc: List<WordByDesc>,
    var cyclicEnabled: Boolean,
    var adjacentEnabled: Boolean
)
