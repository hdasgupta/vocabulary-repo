package play.vocabulary.english.change_one_letter

import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.math.max

class AllWords {
    companion object {
        private val FILE = File("./words_alpha.txt")
        private val URI = URI("https://raw.githubusercontent.com/dwyl/english-words/master/words_alpha.txt")
        private val ALL_WORDS = loadFile()

        fun getWordsOfSize(size: Int): Set<String>? = ALL_WORDS[size]

        fun allWords() = ALL_WORDS

        private fun getInputStream(): InputStream =
            if (FILE.exists()) {
                FILE.inputStream()
            } else {
                val i = URI.toURL().openStream()
                Files.copy(i, FILE.toPath(), StandardCopyOption.REPLACE_EXISTING)
                FILE.inputStream()
            }

        private fun loadFile(minSize: Int = 2): Map<Int, Set<String>> {
            val finalMinSize = max(minSize, 3)
            val inputStream = getInputStream()
            val streamReader = InputStreamReader(inputStream)
            val bufferReader = BufferedReader(streamReader)
            val lines = bufferReader.lines()
            val map = mutableMapOf<Int, TreeSet<String>>()
            lines.forEach {
                if (it.length >= finalMinSize) {
                    if (!map.containsKey(it.length)) {
                        map[it.length] = TreeSet()
                    }
                    map[it.length]!!.add(it)
                }
            }
            return map
        }
    }
}