package play.vocab.playwithvocab.common.initializer

enum class Status(val note: Char) {
    WAITING('?'),
    STARTED('1'),

    //SAVED('2'),
    ONE_LETTER_CHANGE('2'),
    REARRANGE_CHANGE('3'),
    DONE('X'),
}