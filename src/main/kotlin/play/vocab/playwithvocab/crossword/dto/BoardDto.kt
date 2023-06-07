package play.vocab.playwithvocab.crossword.dto

data class BoardDto(
    var leftToRightWords: List<IndexedDesc>,
    var topToBottomWords: List<IndexedDesc>,
    var size: SizeDto,
    var locationToFill: List<LocationDto>,
    var cyclicEnabled: Boolean,
    var adjacentEnabled: Boolean
)
