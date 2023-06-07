package play.vocab.playwithvocab.crossword.commons

import play.vocab.playwithvocab.crossword.enums.WordDirection

data class Word(
    val chars: List<Character>,
    val direction: WordDirection,
    val description: String
) {
    val row:Int
        get() = chars[0].location.row

    val column:Int
        get() = chars[0].location.column

    fun shiftRow(offset:UInt):Int = chars[0].location.shiftRow(offset)

    fun shiftColumn(offset:UInt):Int = chars[0].location.shiftColumn(offset)
}
