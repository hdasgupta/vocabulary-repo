package play.vocab.playwithvocab.crossword.controllers

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import play.vocab.playwithvocab.crossword.dto.*
import play.vocab.playwithvocab.crossword.services.CWGameService
import play.vocab.playwithvocab.crossword.services.CWGameValidationService

@RestController
@CrossOrigin
@RequestMapping(value = ["/api/games/crossword/validate"])
class CWGameValidationController(
    private val gameValidationService: CWGameValidationService
) {

    @PostMapping(value = ["/{reference}"])
    fun validate(@PathVariable reference: String): GameDto =
        gameValidationService.verify(reference)


}