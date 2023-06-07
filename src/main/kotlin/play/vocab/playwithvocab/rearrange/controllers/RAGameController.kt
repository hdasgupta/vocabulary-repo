package play.vocab.playwithvocab.rearrange.controllers

import org.springframework.web.bind.annotation.*
import play.vocab.playwithvocab.rearrange.dto.BasicGameDto
import play.vocab.playwithvocab.rearrange.dto.GameDto
import play.vocab.playwithvocab.rearrange.services.RAGameService

@RestController
@RequestMapping("/api/games/rearrange")
class RAGameController(
    private var gameService: RAGameService
) {
    @GetMapping(value = ["/"])
    fun getAll(): List<BasicGameDto> =
        gameService.getAll()

    @GetMapping(value = ["/get/{reference}"])
    fun get(@PathVariable reference: String): GameDto =
        gameService.get(reference)

    @PostMapping(value = ["/close/{reference}"])
    fun close(@PathVariable reference: String): Boolean =
        gameService.close(reference)

    @PostMapping(value = ["/new"])
    fun new(): GameDto =
        gameService.new()

    @PutMapping(value = ["/add/{reference}/{word}"])
    fun add(@PathVariable reference: String, @PathVariable word: String): GameDto? =
        gameService.add(reference, word)

    @PostMapping(value = ["/validate/{reference}/{word}"])
    fun validate(@PathVariable reference: String, @PathVariable word: String): Boolean =
        gameService.validate(reference, word)

}