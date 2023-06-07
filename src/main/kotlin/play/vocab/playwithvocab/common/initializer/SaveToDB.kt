package play.vocab.playwithvocab.common.initializer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import play.vocabulary.english.change_one_letter.AllWords
import play.vocab.playwithvocab.common.entiries.Relation
import play.vocab.playwithvocab.common.entiries.Word
import play.vocab.playwithvocab.common.plugins.charSet
import play.vocab.playwithvocab.common.plugins.mismatch
import play.vocab.playwithvocab.common.repos.RelationRepo
import play.vocab.playwithvocab.common.repos.WordRepo
import play.vocab.playwithvocab.rearrange.entiries.Rearrange
import play.vocab.playwithvocab.rearrange.repos.RearrangeRepo
import java.util.concurrent.Executors

@Component
class SaveToDB : CommandLineRunner, ApplicationContextAware {
    @Autowired
    lateinit var wordRepo: WordRepo

    @Autowired
    lateinit var rearrangeRepo: RearrangeRepo

    @Autowired
    lateinit var relationRepo: RelationRepo

    var isSaved: Boolean = false

    val rearrangeSet = mutableMapOf<Int, MutableSet<Long>>()

    override fun run(vararg args: String?) {
        if (!isSaved) {
            val executor = Executors.newFixedThreadPool(25)
            println("Saving words...")
            val map = AllWords.allWords().flatMap {
                it.value.map { word ->
                    Word(
                        word = word
                    )
                }
            }.groupBy {
                it.word.length
            }

            val jobs = Jobs(map.keys)

            jobs.print()

            map.keys.sorted().forEach { wordSize ->
                executor.submit {
                    jobs[wordSize]!!.status = Status.STARTED

                    rearrangeSet[wordSize] = mutableSetOf()

                    wordRepo.saveAll(map[wordSize]!!)

                    val charSets = map[wordSize]!!.associate { w ->
                        Pair(w.id, w.charSet)
                    }

                    jobs[wordSize]!!.status = Status.ONE_LETTER_CHANGE
                    map[wordSize]!!.forEach { fixedLengthWord ->
                        val relations = map[wordSize]!!.filter { word ->
                            mismatch(word.word.toList(), fixedLengthWord.word.toList()) == 1
                        }.map {
                            Relation(word = fixedLengthWord, validChange = it)
                        }

                        relationRepo.saveAll(relations)
                    }

                    jobs[wordSize]!!.status = Status.REARRANGE_CHANGE

                    map[wordSize]!!.forEach { fixedLengthWord ->

                        if (!rearrangeSet[wordSize]!!.contains(fixedLengthWord.id)) {
                            val set = charSets[fixedLengthWord.id]!!

                            val rearranges = map[wordSize]!!.filter { word ->
                                mismatch(set, charSets[word.id]!!) == 0
                            }.toSet()

                            if (rearranges.count() > 1) {
                                val rearrange = Rearrange(
                                    interesting = rearranges.size >= wordSize && set.size == set.toSet().size,
                                    usedChars = set,
                                    words = rearranges
                                )

                                rearrangeRepo.save(rearrange)

                                rearrangeSet[wordSize]!!.addAll(
                                    rearranges.map { r ->
                                        r.id!!
                                    }
                                )
                            }
                        }
                    }

                    jobs[wordSize]!!.status = Status.DONE
                }
            }

            jobs.register {
                println()
                println("All Done!!")
                executor.close()
            }

        }

    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        isSaved = wordRepo.findAll().toList().isNotEmpty()
    }
}