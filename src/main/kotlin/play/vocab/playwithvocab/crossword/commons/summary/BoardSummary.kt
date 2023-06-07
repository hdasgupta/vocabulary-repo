package play.vocab.playwithvocab.crossword.commons.summary

import play.vocab.playwithvocab.crossword.commons.Location

data class BoardSummary(
    val size: SizeSummary,
    val options: OptionSummary,
    val wordsLeftToRight: List<WordSummary>,
    val wordsTopToBottom: List<WordSummary>,
    val locationsToFill: List<LocationSummary>
)
