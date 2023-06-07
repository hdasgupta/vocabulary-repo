package play.vocabulary.english.change_one_letter

data class ValidChangeList(val word: String) : Comparable<ValidChangeList> {
    val validList: List<String>? = null;

    override fun compareTo(other: ValidChangeList): Int =
        word.compareTo(other.word)

    override fun equals(other: Any?): Boolean {
        return other is ValidChangeList && word.equals(other.word)
    }

    override fun hashCode(): Int {
        return word.hashCode()
    }
}