package play.vocab.playwithvocab.crossword.dto

data class LocationInputDto(
    var index: Int?,
    var row: Int,
    var column: Int,
    var input:Char?
)
