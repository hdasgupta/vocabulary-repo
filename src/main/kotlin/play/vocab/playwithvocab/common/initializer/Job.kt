package play.vocab.playwithvocab.common.initializer

class Job(val index: Int) {
    var status: Status = Status.WAITING
        get() = field
        set(value) {
            field = value
            hook()
        }

    private var hook: () -> Unit = {}
    override fun toString(): String {
        return " ${status.note} "
    }

    fun register(hook: () -> Unit) {
        this.hook = hook
    }
}