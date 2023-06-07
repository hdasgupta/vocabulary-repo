package play.vocab.playwithvocab.common.initializer

import play.vocab.playwithvocab.common.plugins.format

class Jobs(indices: Set<Int>) : ArrayList<Job?>() {
    private val length = indices.max()
    private var printed = false
    private var hook: () -> Unit = {}

    init {
        (0..length).forEach {
            if (!indices.contains(it)) {
                add(null)
            } else {
                val job = Job(it)
                job.register {
                    print()
                    if (
                        filterNotNull().none { job ->
                            job.status != Status.DONE
                        }
                    )
                        hook()
                }
                this.add(job)
            }
        }
    }

    fun register(hook: () -> Unit) {
        this.hook = hook
    }

    override fun toString(): String {
        return this.joinToString("") {
            if (it == null) {
                " - "
            } else {
                "$it"
            }
        }
    }

    @Synchronized
    fun print() {
        if (printed)
            print("\b".repeat((length + 1) * 3))
        else {
            (0..length).forEach {
                print(" ${it.format(2)}")
            }
            println()
        }
        print("$this")
        printed = true
    }
}