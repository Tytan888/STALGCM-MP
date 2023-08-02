import java.util.LinkedList

class State(private val name: String, private val transitions: LinkedList<Transition>, private val isStart: Boolean, private val isFinal: Boolean) {

    fun getName(): String {
        return name
    }

    fun getTransitions(): LinkedList<Transition> {
        return transitions
    }

    fun getIsFinal(): Boolean {
        return isFinal
    }

}