package play.vocab.playwithvocab.crossword.builders

import play.vocab.playwithvocab.crossword.commons.Character
import play.vocab.playwithvocab.crossword.commons.Location
import play.vocab.playwithvocab.crossword.commons.Word
import play.vocab.playwithvocab.crossword.enums.WordDirection

class WordBuilder(
    private val word: String,
    private val description: String,
    private val direction: WordDirection,
    private val location: Location
) {
    fun build():Word =
        Word(
            word.mapIndexed {
                index, char ->
                    Character(
                        char,
                        index,
                        location.shift(index, direction)
                    )
            },
            direction,
            description
        )

}