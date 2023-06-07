package play.vocab.playwithvocab.change_one_letter.dto

data class TurnDto(
    var chars: List<Char>,
    var player: Int,
    var isComputer:Boolean
) {
}