package play.vocab.playwithvocab.crossword.dto

data class WordsDto(
    var leftToRight: List<IndexedDesc>,
    var topToBottom: List<IndexedDesc>,
)
