package play.vocab.playwithvocab.guess_the_word.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import play.vocab.playwithvocab.guess_the_word.common.GuessType
import play.vocab.playwithvocab.guess_the_word.dto.BasicGameDto
import play.vocab.playwithvocab.guess_the_word.dto.GameDto
import play.vocab.playwithvocab.guess_the_word.services.GTWGameService

@RestController
@CrossOrigin
@RequestMapping(value = ["/api/games/guess-the-word"])
class GTWGameController(
    private var gameService: GTWGameService
) {
    @GetMapping(value = ["/"])
    fun getAll(): List<BasicGameDto> =
        gameService.getAll()

    @GetMapping(value = ["/get/{reference}"])
    fun get(@PathVariable reference: String): GameDto =
        gameService.get(reference)

    @PostMapping(value = ["/new/{size}"])
    fun new(@PathVariable size: Int): GameDto =
        gameService.new(size)

    @PutMapping(value = ["/add/{reference}/{word}"])
    fun add(@PathVariable reference: String, @PathVariable word: String): GameDto? =
        gameService.addWord(reference, word)

    @PostMapping(value = ["/validate/{reference}/{word}"])
    fun validate(@PathVariable reference: String, @PathVariable word: String): Boolean =
        gameService.isValidWord(reference, word)

    @PutMapping(value = ["/guess-type/{reference}/{char}/{type}"])
    fun add(@PathVariable reference: String, @PathVariable char: Char, @PathVariable type:GuessType): Map<Char, GuessType> =
        gameService.changeGuessType(reference, char, type)
}