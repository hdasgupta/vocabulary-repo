package play.vocab.playwithvocab.rearrange.plugins

import play.vocab.playwithvocab.rearrange.entiries.Rearrange
import play.vocab.playwithvocab.rearrange.repos.RearrangeRepo

fun Int.pow(x: Int): Int =
    if(x==0) {
        1
    } else {
        this*this.pow(x-1)
    }