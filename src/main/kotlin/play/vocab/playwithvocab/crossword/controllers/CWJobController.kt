package play.vocab.playwithvocab.crossword.controllers

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import play.vocab.playwithvocab.crossword.dto.*
import play.vocab.playwithvocab.crossword.services.CWBoardJobService
import play.vocab.playwithvocab.crossword.services.CWBoardService
import play.vocab.playwithvocab.crossword.services.CWGameService

@RestController
@CrossOrigin
@RequestMapping(value = ["/api/games/crossword/jobs"])
class CWJobController(
    private val jobService: CWBoardJobService
) {
    @GetMapping(value = ["/{page}/{size}"])
    fun getAllJobs(@PathVariable page: Int, @PathVariable size: Int): Page<JobDto> =
        jobService.getAll(page, size)

    @GetMapping(value = ["/get/{reference}"])
    fun getBoard(@PathVariable reference: String): JobDto =
        jobService.getJob(reference)

    @PostMapping(value = ["/create"])
    fun createBoard(@RequestBody board: BoardDetailsDto): JobDto =
        jobService.create(board)

}