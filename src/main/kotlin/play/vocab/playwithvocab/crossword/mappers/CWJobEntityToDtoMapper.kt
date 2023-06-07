package play.vocab.playwithvocab.crossword.mappers

import org.springframework.stereotype.Component
import play.vocab.playwithvocab.crossword.dto.JobDto
import play.vocab.playwithvocab.crossword.dto.WordByDesc
import play.vocab.playwithvocab.crossword.entities.CWBoardCreationJob
import java.text.SimpleDateFormat

@Component
class CWJobEntityToDtoMapper {
    val dateFormatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.MEDIUM)

    fun getJobDto(job: CWBoardCreationJob): JobDto =
        JobDto(
            job.reference,
            job.words.map {
                WordByDesc(
                    it.word.word,
                    it.description
                )
            },
            job.options,
            job.status,
            job.message,
            dateFormatter.format(job.updatedAt)
        )
}