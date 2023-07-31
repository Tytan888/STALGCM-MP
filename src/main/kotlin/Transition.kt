class Transition(from: String, read: String, pop: String, to: String, push: String) : Comparable<Transition> {
    private val from: String = from
    private val read: String = read
    private val pop: String = pop
    private val to: String = to
    private val push: String = push

    fun getFrom(): String {
        return from
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