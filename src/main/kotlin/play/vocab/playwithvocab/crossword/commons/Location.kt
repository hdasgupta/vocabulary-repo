package play.vocab.playwithvocab.crossword.commons

import play.vocab.playwithvocab.crossword.enums.WordDirection

data class Location(
    private var _row:Int,
    private var _column:Int
) {
    val row:Int
        get() = _row

    val column:Int
        get() = _column

    fun shiftRow(offset:UInt):Int {
        _row += offset.toInt()
        return _row
    }

    fun shiftColumn(offset:UInt):Int {
        _column += offset.toInt()
        return _column
    }

    fun shift(offset:Int, direction: WordDirection):Location =
        when(direction) {
            WordDirection.LeftToRight -> Location(row, column + offset)
            WordDirection.TopToBottom -> Location(row + offset, column)
        }

    fun shiftMe(offset:Int, direction: WordDirection): Unit =
        when(direction) {
            WordDirection.LeftToRight -> _column += offset
            WordDirection.TopToBottom -> _row += offset
        }

    override fun toString(): String {
        return "$row,$column"
    }
}
