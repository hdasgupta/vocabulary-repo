package play.vocab.playwithvocab.crossword.dto

import play.vocab.playwithvocab.crossword.enums.JobStatus

data class JobDto(
    var id: String,
    var words: List<WordByDesc>,
    var options: Int?,
    var status: JobStatus,
    var message: String,
    var lastUpdated: String
)
