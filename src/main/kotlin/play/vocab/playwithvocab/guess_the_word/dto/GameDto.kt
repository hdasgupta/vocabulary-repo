package play.vocab.playwithvocab.guess_the_word.dto

import play.vocab.playwithvocab.guess_the_word.common.GuessType

class GameDto(
    var id: String,
    var attempts:Int,
    var wordLength:Int,
    var point:Int,
    var guessedWords:List<MatchDto>,
    var guessChart:Map<Char, GuessType>,
    var isClosed:Boolean
) {
}