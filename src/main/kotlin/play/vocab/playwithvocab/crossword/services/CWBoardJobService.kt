package play.vocab.playwithvocab.crossword.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import play.vocab.playwithvocab.common.repos.WordRepo
import play.vocab.playwithvocab.crossword.builders.BoardBuilder
import play.vocab.playwithvocab.crossword.commons.summary.BoardSummary
import play.vocab.playwithvocab.crossword.dto.BoardDetailsDto
import play.vocab.playwithvocab.crossword.dto.JobDto
import play.vocab.playwithvocab.crossword.entities.*
import play.vocab.playwithvocab.crossword.enums.JobStatus
import play.vocab.playwithvocab.crossword.enums.WordDirection
import play.vocab.playwithvocab.crossword.mappers.CWJobEntityToDtoMapper
import play.vocab.playwithvocab.crossword.repos.*
import java.util.concurrent.Executors

@Service
class CWBoardJobService(
    private val wordRepo: WordRepo,
    private val boardRepo: CWBoardRepo,
    private val locationRepo: CWLocationRepo,
    private val cwWordRepo: CWWordRepo,
    private val descRepo: CWWordDescRepo,
    private val jobRepo: CWJobRepo,
    private val jobMapper: CWJobEntityToDtoMapper
) {
    val executor = Executors.newFixedThreadPool(5)

    fun getAll(page:Int, size:Int):Page<JobDto> =
        jobRepo.findAll(
            Pageable.ofSize(size)
                .withPage(page)
        ).map {
            jobMapper.getJobDto(it)
        }

    fun create(board: BoardDetailsDto): JobDto {
        val words = getWords(board)
        val job = jobRepo.save(
            CWBoardCreationJob(
                words = words.values.toList(),
                status = JobStatus.Started,
                message = "Job Started"
            )
        )

        executor.submit {
                doJob(board, words, job)
            }

        return jobMapper.getJobDto(job)

    }

    fun getJob(reference: String): JobDto =
        jobMapper.getJobDto(
            jobRepo.findByReference(reference)
        )

    private fun doJob(
        board: BoardDetailsDto,
        words: Map<String, CWWordDescriptions>,
        job: CWBoardCreationJob
    ): Unit {
        job.status = JobStatus.Running
        job.message = "Job Running"
        job.updated()

        jobRepo.save(job)

        try {
            val wordDesc = board.wordByDesc
                .associate {
                    Pair(it.word, it.desc)
                }

            val builder = BoardBuilder(board.cyclicEnabled, board.adjacentEnabled)

            val boardSummaries = builder.build(wordDesc)
                .map {
                    it.summary()
                }

            job.options = boardSummaries
                .map {
                    getBoard(it, job, words)
                }.count()
            job.message = "Job Ended Successfully"
            job.status = JobStatus.Completed
            job.updated()
            jobRepo.save(job)

        } catch (e: Throwable) {
            job.message = e.message ?: "Job Ended with Error"
            job.status = JobStatus.Error
            job.updated()
            jobRepo.save(job)
        }
    }

    private fun getBoard(
        boardSummary: BoardSummary,
        job: CWBoardCreationJob,
        words: Map<String, CWWordDescriptions>
    ): CWBoards {
        var b = CWBoards(
            job = job,
            size = CWSize(
                boardSummary.size.row,
                boardSummary.size.column
            ),
            option = CWOptions(
                boardSummary.options.enableCyclic,
                boardSummary.options.enableAdjacent
            )
        )

        val locations = boardSummary.locationsToFill
            .map { loc ->
                CWLocations(
                    board = b,
                    index = loc.index,
                    row = loc.row,
                    col = loc.column
                )
            }

        b = boardRepo.save(b)

        cwWordRepo.saveAll(
            boardSummary.wordsLeftToRight
                .map { w ->
                    CWWordByBoard(
                        board = boardRepo.findByReference(b.reference),
                        wordDesc = words[w.word]!!,
                        index = w.index,
                        direction = WordDirection.LeftToRight
                    )
                }
        )

        cwWordRepo.saveAll(
            boardSummary.wordsTopToBottom
                .map { w ->
                    CWWordByBoard(
                        board = boardRepo.findByReference(b.reference),
                        wordDesc = words[w.word]!!,
                        index = w.index,
                        direction = WordDirection.TopToBottom
                    )
                }
        )

        locationRepo.saveAll(locations)

        return b
    }

    private fun getWords(board: BoardDetailsDto) = descRepo.saveAll(
        board.wordByDesc.map {
            CWWordDescriptions(
                word = wordRepo.findByWordIgnoreCase(it.word)!!,
                description = it.desc
            )
        }
    ).associateBy {
        it.word.word
    }

}