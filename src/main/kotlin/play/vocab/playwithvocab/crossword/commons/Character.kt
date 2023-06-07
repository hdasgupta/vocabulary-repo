package play.vocab.playwithvocab.crossword.commons

data class Character(
    val char: Char,
    val index: Int,
    val location: Location
) {
    val row:Int
        get() = location.row

    val column:Int
        get() = location.column

    fun shiftRow(offset:UInt):Int = location.shiftRow(offset)

    fun shiftColumn(offset:UInt):Int = location.shiftColumn(offset)
}
