import java.io.BufferedReader
import java.io.File
import java.util.LinkedList
import kotlin.reflect.typeOf

class Controller {

    init {
        val bufferedReader: BufferedReader = File("input1.txt").bufferedReader()

        val Q = bufferedReader.readLine().split(" ")
        val Sigma = bufferedReader.readLine().split(" ")
        val Gamma = bufferedReader.readLine().split(" ")

        val carDelta = bufferedReader.readLine().toInt()
        val Delta = LinkedList<Transition>()
        for (i in 0..<carDelta) {
            val line = bufferedReader.readLine().split(" ")
            Delta.add(Transition(line[0], line[1], line[2], line[3], line[4]))
        }
        Delta.sort()

        val qI = bufferedReader.readLine()
        val F = bufferedReader.readLine().split(" ")

        val uniqueInputs = LinkedList<String>()
        uniqueInputs.add("")
        for (transition in Delta) {
            if (transition.formatInput() != uniqueInputs.last()) {
                uniqueInputs.add(transition.formatInput())
            }
        }
        uniqueInputs.removeAt(0)

        println("Q: $Q "+ Q::class.simpleName)
        println("Sigma: $Sigma "+Sigma::class.simpleName)
        println("Gamma: $Gamma "+Gamma::class.simpleName)
        println("Delta: $Delta "+Delta::class.simpleName)
        println("qI: $qI "+qI::class.simpleName)
        println("F: $F "+F::class.simpleName)
        println("Unique inputs: $uniqueInputs")

        val view = View(Q, Delta, qI, F, uniqueInputs)
        val model = Model(Q, Sigma, Gamma, Delta, qI, F)
        println("i got here")
        model.initializeMachine()
        var inputString="0011"
        var i=0
        var status=0
        var valid=false

        do{
            if(i<inputString.length){
                status=model.transitionState(inputString[i].toString())
            }else{
                status=model.transitionState("")
            }
            println(model.getCurrentState()?.getName())
            println(model.getStack())
            if(model.getCurrentState()==null){
                println("no more transitions")
            }
            if(status==1){
                i++
            }
            if(model.getCurrentState()?.getIsFinal() ==true && i>=inputString.length){
                valid=true
            }
        }while(status!=0)
        if(valid==true){
            println("accepted")
        }else{
            println("rejected")
        }

        // TODO TEMP
        view.turnInputFieldtoLabel()
        view.highlightInputLabel(3)
        view.setHighlightedTransition(2)
        view.addStackItem("Z")
        view.addStackItem("X")
    }

}

fun main() {
    Controller()
}