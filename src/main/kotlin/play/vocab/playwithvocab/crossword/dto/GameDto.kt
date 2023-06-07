package play.vocab.playwithvocab.crossword.dto

data class GameDto(
    var id: String,
    var words: WordsDto,
    var locationsToFill: List<LocationDto>,
    var size: SizeDto,
    var canSubmit:Boolean,
    var correct: Boolean?
)
