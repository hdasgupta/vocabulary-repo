package play.vocab.playwithvocab.crossword.controllers

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import play.vocab.playwithvocab.crossword.dto.*
import play.vocab.playwithvocab.crossword.services.CWBoardService
import play.vocab.playwithvocab.crossword.services.CWGameService

@RestController
@CrossOrigin
@RequestMapping(value = ["/api/games/crossword/boards"])
class CWBoardController(
    private val boardService: CWBoardService
) {
    @GetMapping(value = ["/"])
    fun getAllBoard(@RequestParam page: Int, @RequestParam size: Int): Page<BasicBoardDto> =
        boardService.getAllBoard(page, size)

    @GetMapping(value = ["/by-job/{reference}/{page}"])
    fun getAllBoard(@PathVariable reference: String, @PathVariable page: Int): Page<BasicBoardDto> =
        boardService.getAllBoardByJob(reference, page)

    @GetMapping(value = ["/get/{reference}"])
    fun getBoard(@PathVariable reference: String): BoardDto =
        boardService.getBoard(reference)

}