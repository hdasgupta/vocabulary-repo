package play.vocab.playwithvocab.crossword.controllers

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import play.vocab.playwithvocab.crossword.dto.*
import play.vocab.playwithvocab.crossword.services.CWGameService

@RestController
@CrossOrigin
@RequestMapping(value = ["/api/games/crossword"])
class CWGameController(
    private val gameService: CWGameService
) {
    @GetMapping(value = ["/"])
    fun getAll(): List<BasicGameDto> =
        gameService.getAll()

    @GetMapping(value = ["/get/{reference}"])
    fun get(@PathVariable reference: String): GameDto =
        gameService.get(reference)

    @PostMapping(value = ["/new"])
    fun new(): GameDto =
        gameService.new()

    @PutMapping(value = ["/put/{reference}/{row}/{column}/{input}"])
    fun add(@PathVariable reference: String, @PathVariable row: Int, @PathVariable column: Int, @PathVariable input: Char): GameDto =
        gameService.put(reference, row, column, input)

}