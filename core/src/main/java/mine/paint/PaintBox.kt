package mine.paint

class PaintBox {
    private var max_x: Int = 0
    var x: Int = 0
        private set
    private var max_y: Int = 0
    var y: Int = 0
        private set

    constructor() {
        max_x = Integer.MIN_VALUE
        x = Integer.MAX_VALUE
        max_y = Integer.MIN_VALUE
        y = Integer.MAX_VALUE
    }

    constructor(x: Int, y: Int, w: Int, h: Int) {
        max_x = x + w
        this.x = x
        max_y = y + h
        this.y = y
    }

    fun add(x: Int, y: Int) {
        if (x > max_x) max_x = x
        if (x < this.x) this.x = x
        if (y > max_y) max_y = y
        if (y < this.y) this.y = y
    }

    fun add(x: Int, y: Int, w: Int, h: Int) {
        if (x + w > max_x) max_x = x + w
        if (x < this.x) this.x = x
        if (y + h > max_y) max_y = y + h
        if (y < this.y) this.y = y
    }

    val w: Int
        get() = max_x - x
    val h: Int
        get() = max_y - y
}
