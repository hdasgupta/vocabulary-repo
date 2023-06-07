package play.vocab.playwithvocab.crossword.plugins

import play.vocab.playwithvocab.crossword.enums.WordDirection
import play.vocab.playwithvocab.crossword.repos.CWWordRepo

fun WordDirection.reverse(): WordDirection =
    when(this) {
        WordDirection.LeftToRight -> WordDirection.TopToBottom
        WordDirection.TopToBottom -> WordDirection.LeftToRight
    }

fun CWWordRepo.findAllLTRWordByBoardReference(boardReference: String) =
    this.findAllByBoardReferenceAndDirection(boardReference, WordDirection.LeftToRight)

fun CWWordRepo.findAllTTBWordByBoardReference(boardReference: String) =
    this.findAllByBoardReferenceAndDirection(boardReference, WordDirection.TopToBottom)
