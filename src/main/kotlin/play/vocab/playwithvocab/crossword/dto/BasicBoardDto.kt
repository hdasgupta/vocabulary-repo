package play.vocab.playwithvocab.crossword.dto

data class BasicBoardDto(
    val id: String,
    val inputs: List<LocationInputDto>,
    val size: SizeDto
)
