package play.vocab.playwithvocab

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages = ["play.vocab.playwithvocab"])
class PlayWithVocabularyApplication

fun main(args: Array<String>) {
    runApplication<PlayWithVocabularyApplication>(*args)
}
