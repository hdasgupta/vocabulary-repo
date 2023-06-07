package play.vocab.playwithvocab.common.enums

enum class WordContent(val available: String) {
    alphabetic(('a'..'z').joinToString("")),
    numeric(('0'..'9').joinToString("")),
    alphanumeric(('0'..'9').joinToString("")+('a'..'z').joinToString("")),
}