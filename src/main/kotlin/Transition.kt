class Transition(
    private val from: String,
    private val read: String,
    private val pop: String,
    private val to: String,
    private val push: String
) : Comparable<Transition> {

    fun getFrom(): String {
        return from
    }

    fun getRead(): String {
        return read
    }

    fun getPop(): String {
        return pop
    }

    fun getTo(): String {
        return to
    }

    fun getPush(): String {
        return push
    }

    fun formatInput(): String {
        return "($read, $pop)"
    }

    fun formatOutput(): String {
        return "($to, $push)"
    }

    override fun compareTo(other: Transition): Int {
        return formatInput().compareTo(other.formatInput())
    }

    override fun toString(): String {
        return "δ($from, $to, $pop)" + " = " + formatOutput()
    }
}