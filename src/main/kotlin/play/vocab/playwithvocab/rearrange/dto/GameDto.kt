package play.vocab.playwithvocab.rearrange.dto

data class GameDto(
    var id:String,
    var chars: List<Char>,
    var guessedWords: List<String>,
    var closable: Boolean,
    var points: Int,
)
