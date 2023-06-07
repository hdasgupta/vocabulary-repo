package play.vocab.playwithvocab.crossword.commons

import play.vocab.playwithvocab.crossword.builders.WordBuilder
import play.vocab.playwithvocab.crossword.commons.summary.*
import play.vocab.playwithvocab.crossword.enums.WordDirection
import play.vocab.playwithvocab.crossword.plugins.reverse
import java.util.TreeMap

data class Board (
    private val words:List<Word>,
    private val enableCyclic: Boolean,
    private val enableAdjacent: Boolean,
) {

    fun verify(words: List<Word>, enableCyclic: Boolean, enableAdjacent: Boolean): Boolean {
        val locationChar: MutableMap<String, Char> = mutableMapOf()
        val locationWords: MutableMap<String, MutableList<Word>> = mutableMapOf()
        var output = true

        words.forEach {
            it.chars.forEach {
                character ->
                val key = "${character.location}"

                if(locationWords.containsKey(key)) {
                    if(locationWords[key]!!.size >= 2) {
                        output = false
                    } else {
                        locationWords[key]!!.add(it)
                    }

                } else {
                    locationWords[key] = mutableListOf(it)
                }

                if(locationChar.containsKey(key)) {
                    if(locationChar[key]!! != character.char) {
                        output = false
                    }

                } else {
                    locationChar[key] = character.char
                }
            }

        }

        if(!enableCyclic && locationWords.values.count {
            it.contains(words.last())
        } > 1) {
            output = false
        }

        locationWords.filter {
            it.value.contains(words.last()) && it.value.size > 1
        }.forEach {
            val index = it.value[0].chars.filter {
                character ->
                "${character.location}" == it.key
            }[0].index

            if(index>0 && locationWords["${it.value[0].chars[index-1].location}"]!!.size > 1 && !enableAdjacent) {
                output = false
            } else if(index < (it.value[0].chars.size -1) && locationWords["${it.value[0].chars[index+1].location}"]!!.size > 1 && !enableAdjacent) {
                output = false
            }
        }
        return output
    }

    fun standardize(words: List<Word>): List<Word> {
        val lastWord = words.last();

        val min:Int = lastWord.chars.minOfOrNull {
            when(lastWord.direction) {
                WordDirection.LeftToRight -> it.location.column
                WordDirection.TopToBottom -> it.location.row
            }
        }!!

        return if(min < 0) {
            words.map {
                word ->
                    Word(
                        word.chars.map {
                                character ->
                            Character(
                                character.char,
                                character.index,
                                character.location.shift(-min, lastWord.direction)
                            )
                        },
                        word.direction,
                        word.description
                    )

            }
        } else {
            words
        }
    }

    fun insert(option: InsertOption): Set<Board> {
        val output: MutableSet<Board> = mutableSetOf()

        if(words.isEmpty()) {
            output.add(
                Board(
                    listOf(
                        WordBuilder(
                            option.word,
                            option.description,
                            option.direction,
                            Location(0, 0)
                        ).build()
                    ),
                    enableCyclic,
                    enableAdjacent
                )

            )
        } else {
            words.filter {
                it.direction == option.direction.reverse()
            }.map {
                it.chars
                    .flatMap {
                        character ->
                            findIndices(option.word, character.char)
                                .map {
                                    i ->
                                        val newWords = words.toMutableList()
                                        newWords.add(
                                            WordBuilder(
                                                option.word,
                                                option.description,
                                                option.direction,
                                                character.location.shift(
                                                    -i,
                                                    option.direction
                                                )
                                            ).build()
                                        )
                                        newWords
                                }
                    }.filter {
                        words -> verify(words, option.enableCyclic, option.enableAdjacent)
                    }.forEach {
                        words->
                        val newWords = standardize(words)
                        output.add(Board(newWords, enableCyclic, enableAdjacent))
                    }
            }
        }

        return output
    }

    fun summary(): BoardSummary {
        val locationChars: TreeMap<String, MutableList<Character>> = TreeMap()
        val locationWords: TreeMap<String, MutableList<Word>> = TreeMap()
        val wordMap:MutableMap<WordDirection, MutableList<WordSummary>> =
            WordDirection.values()
                .associate {
                    Pair<WordDirection, MutableList<WordSummary>>(
                        it,
                        mutableListOf()
                    )
                }
                .toMutableMap()
        var index = 1
        val sizeSummary = SizeSummary(
            words.flatMap {
                it.chars.map {
                        character -> character.location.row
                }
            }.max() + 1,
            words.flatMap {
                it.chars.map {
                        character -> character.location.column
                }
            }.max() + 1
        )

        words.forEach {
            it.chars.forEach {
                    character ->
                val key = "${character.location}"

                if(locationChars.containsKey(key)) {
                    locationChars[key]!!.add(character)
                } else {
                    locationChars[key] = mutableListOf(character)
                }

                if(locationWords.containsKey(key)) {
                    locationWords[key]!!.add(it)
                } else {
                    locationWords[key] = mutableListOf(it)
                }

            }
        }

        val locations = locationChars.map {
            val ints = it.key.split(",")
                .map {
                        num -> num.toInt()
                }
            if(it.value.any { character -> character.index==0 }) {
                val indices = it.value.mapIndexedNotNull{
                    i, character ->
                        if(character.index==0) {
                            i
                        } else {
                            null
                        }
                }
                indices.forEach {
                    i ->
                    val word = locationWords[it.key]!![i]
                    wordMap[word.direction]!!.add(
                        WordSummary(
                            index,
                            word.chars.joinToString("") {
                                character -> "${character.char}"
                            },
                            word.description
                        )
                    )
                }

                LocationSummary(
                    index++,
                    ints[0],
                    ints[1]
                )
            } else {
                LocationSummary(
                    null,
                    ints[0],
                    ints[1]
                )
            }
        }



        return BoardSummary(
            sizeSummary,
            OptionSummary(
                enableCyclic,
                enableAdjacent
            ),
            wordMap[WordDirection.LeftToRight]!!,
            wordMap[WordDirection.TopToBottom]!!,
            locations
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        return "$this" == "$other"
    }

    override fun hashCode(): Int {
        return words.hashCode()
    }

    override fun toString(): String {
        val minRow = words.flatMap {
            it.chars.map {
                    character -> character.location.row
            }
        }.min()
        val minColumn = words.flatMap {
            it.chars.map {
                    character -> character.location.column
            }
        }.min()
        val maxRow = words.flatMap {
            it.chars.map {
                character -> character.location.row
            }
        }.max()
        val maxColumn = words.flatMap {
            it.chars.map {
                    character -> character.location.column
            }
        }.max()

        val rows = maxRow - minRow + 1
        val columns = maxColumn - minColumn + 1
        val rowOffset = -minRow
        val columnOffset = -minColumn
        val total = rows * columns

        val str = StringBuffer(" ".repeat(total))

        println("rows: $rows -> min: $minRow, max: $maxRow, offset: $rowOffset")
        println("columns: $columns-> min: $minColumn, max: $maxColumn, offset: $columnOffset")
        println("total: $total")

        words.forEach {
            it.chars.forEach {
                character ->
                val index = ((character.location.row+rowOffset)*columns)+character.location.column+columnOffset
                if(index >=total) {
                    println("index: $index")
                    println("row: ${character.location.row}")
                    println("column: ${character.location.column}")
                }
                str.setCharAt(index, character.char)
            }
        }

        return "$str"
    }

    fun findIndices(word: String, char: Char): List<Int> =
        word.indices
            .filter {
                word[it]==char
            }

}