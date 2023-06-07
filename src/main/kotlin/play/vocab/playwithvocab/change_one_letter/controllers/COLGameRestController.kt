package play.vocab.playwithvocab.change_one_letter.controllers

import org.springframework.web.bind.annotation.*
import play.vocab.playwithvocab.change_one_letter.dto.BasicGameDto
import play.vocab.playwithvocab.change_one_letter.dto.GameDto
import play.vocab.playwithvocab.change_one_letter.services.COLGameService

@RestController
@CrossOrigin
@RequestMapping(value = ["/api/games/change_one_letter"])
class COLGameRestController(
    private var gameService: COLGameService
) {

    @GetMapping(value = ["/"])
    fun getAll(): List<BasicGameDto> =
        gameService.getAll()

    @GetMapping(value = ["/get/{reference}"])
    fun get(@PathVariable reference: String): GameDto =
        gameService.getGame(reference)

    @PostMapping(value = ["/new"])
    fun new(): GameDto = gameService.newGame(
        gameService.randomWord(),
        2,
        setOf(1)
    )!!


    @PostMapping(value = ["/move"])
    fun move(@RequestParam gameId: String, @RequestParam index: Int, @RequestParam input: Char): GameDto {
        gameService.move(
            gameId,
            index,
            input
        )

        return gameService.getGame(gameId)
    }


}