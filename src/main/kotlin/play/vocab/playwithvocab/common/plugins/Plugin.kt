package play.vocab.playwithvocab.common.plugins

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.common.enums.WordContent
import play.vocab.playwithvocab.common.repos.WordRepo

const val REFERENCE_STRING_LENGTH: Int = 20

operator fun StringBuffer.set(index: Int, value: Char) {
    this.setCharAt(index, value)
}

fun Int.format(digit: Int = 2): String =
    "%0${digit}d".format(this)

operator fun <T : Any> Collection<T>.get(index: Int): T =
    this.elementAt(index)

fun <T : Comparable<T>> MutableList<T>.sortedAdd(value: T): Unit =
    if (this.isEmpty()) {
        this.add(value)
        Unit
    } else {
        val invertedInsertionPoint = this.binarySearch(value)
        val actualInsertionPoint = -(invertedInsertionPoint + 1)

        this.add(actualInsertionPoint, value)
    }

fun <T : Any> mismatch(collection1: Collection<T>, collection2: Collection<T>): Int =
    if (collection1.size == collection2.size) {
        collection1.indices
            .count {
                collection1[it] != collection2[it]
            }
    } else {
        -1
    }

val Word.charSet: List<Char>
    get() = this.word.toList().sorted()

fun WordRepo.randomWord(min:Int, max:Int): String {
    val index = (0 until  this.countByValidListIsNotEmptyAndSizeGreaterThanEqualAndSizeLessThanEqual(
        min,
        max
    )).random()
    return this.findAllByValidListIsNotEmptyAndSizeGreaterThanEqualAndSizeLessThanEqual(
        min,
        max,
        Pageable.ofSize(1).withPage(index)
    )[0].word
}

fun Int.randomString(content:WordContent=WordContent.alphabetic):String =
    (1..this).joinToString("") {
        "${content.available.random()}"
    }

operator fun <T: Any> Page<T>.get(index:Int):T =
    this.elementAt(index)
