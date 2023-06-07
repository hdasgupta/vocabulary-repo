package play.vocab.playwithvocab.change_one_letter.dto

data class GameDto(
    var id: String,
    var usedWords: List<TurnDto>,
    var options: List<List<Char>>
) {
    var isClosed: Boolean = options.all { it.isEmpty() }
}