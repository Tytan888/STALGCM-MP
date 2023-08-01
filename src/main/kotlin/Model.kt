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
    private val Delta: LinkedList<Transition> = Delta
    private val qI: String = qI
    private val F: List<String> = F
    private var states = LinkedList<State>()
    private var stack = Stack<String>()
    private var currentState:State?=null
    private var currentTransition:Transition?=null
    fun initializeMachine() {
        stack.push("Z")
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
            if(isStart==true){
                this.currentState=State(name, stateTransitions, isStart, isFinal)
            }

        }
    }
    //return a 0 when nothing aka a transition to {} is used
    // return a 1 if a non-lambda transition was used
    //return 2 when a lambda transition is used
    fun transitionState(readSymbol: String): Int {
        var stateTransitions = currentState?.getTransitions()
        var found=false
        if (stateTransitions != null) {
            for (transition in stateTransitions) {

                if (transition.getRead() == readSymbol && (transition.getPop() == stack.peek() || transition.getPop()=="λ")) {
                    if (transition.getPop() != "λ")
                        stack.pop()
                    if (transition.getPush() != "λ")
                        stack.push(transition.getPush())
                    var destinationState = transition.getTo()
                    for(state in states) {
                        if (state.getName() == transition.getTo()) {
                            this.currentState=state
                            this.currentTransition=transition
                            return 1
                        }
                    }
                    found=true
                }
                if(!found){
                    for(transition in stateTransitions){
                        if(transition.getRead()=="λ" && (transition.getPop() == stack.peek() || transition.getPop()=="λ")){
                            if (transition.getPop() != "λ")
                                stack.pop()
                            if (transition.getPush() != "λ")
                                stack.push(transition.getPush())
                            var destinationState = transition.getTo()
                            for(state in states) {
                                if (state.getName() == transition.getTo()) {
                                    this.currentState=state
                                    this.currentTransition=transition
                                    return 2
                                }
                            }
                        }
                    }
                }
            }
        }
        this.currentState=null
        this.currentTransition=null
        return 0
    }

    fun getCurrentTransition():Transition?{
        return currentTransition
    }
    fun getStack():Stack<String>{
        return stack
    }
    fun getCurrentState():State?{
        return currentState
    }

    fun checkFinished(currentState: State):Boolean{
        if(currentState.getIsFinal()==true && stack.empty()==true){
            return true
        }
        return false
    }
}
