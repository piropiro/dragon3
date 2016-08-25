package mine.thread


class Lock {

    @Volatile private var lock: Boolean = false

    init {
        lock = false
    }

    @Synchronized fun lock(): Boolean {
        if (lock) {
            return false
        } else {
            lock = true
            return true
        }
    }

    @Synchronized fun unlock() {
        lock = false
    }
}
