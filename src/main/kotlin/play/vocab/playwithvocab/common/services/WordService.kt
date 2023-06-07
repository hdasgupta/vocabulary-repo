package play.vocab.playwithvocab.common.services

import org.springframework.stereotype.Service
import play.vocab.playwithvocab.common.repos.WordRepo

@Service
class WordService(
    private val wordRepo: WordRepo
) {
    fun isValidWord(word: String): Boolean = true

}