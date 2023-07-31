import java.io.BufferedReader
import java.io.File
import java.util.LinkedList
import java.util.Stack

class Model(
    Q: List<String>,
    Sigma: List<String>,
    Gamma: List<String>,
    Delta: LinkedList<Transition>,
    qI: String,
    F: List<String>
) {
    private val Q: List<String> = Q
    private val Sigma: List<String> = Sigma
    private val Gamma: List<String> = Gamma
    private val Delta: LinkedList<Transition> = Delta;
    private val qI: String = qI;
    private val F: List<String> = F
    private var states = LinkedList<State>()
    private var stack = Stack<String>()

    fun initializeMachine() {

        for (name in Q) {
            var stateTransitions = LinkedList<Transition>()
            var isStart: Boolean = false
            var isFinal: Boolean = false
            if (name == qI) {
                isStart = true
            }
            for (transition in Delta) {
                if (transition.getFrom() == name) {
                    stateTransitions.add(transition);
                }
            }
            for (final in F) {
                if (final == name) {
                    isFinal = true
                }
            }

            states.add(State(name, stateTransitions, isStart, isFinal))

        }
    }

    fun transitionState(currentState: State, readSymbol: String): State {
        var stateTransitions = currentState.getTransitions()

        for (transition in stateTransitions) {
            if (transition.getRead() == readSymbol && transition.getPop() == stack.peek()) {
                if (transition.getPop() != "λ")
                    stack.pop()
                if (transition.getPush() != "λ")
                    stack.push(transition.getPush())
                return transition.getTo()
            }
        }

        return null!!
    }


}
