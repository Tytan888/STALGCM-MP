import java.io.BufferedReader
import java.io.File
import java.util.LinkedList

class Controller {

    init {
        val bufferedReader: BufferedReader = File("input.txt").bufferedReader()

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

        println("Q: $Q")
        println("Sigma: $Sigma")
        println("Gamma: $Gamma")
        println("Delta: $Delta")
        println("qI: $qI")
        println("F: $F")
        println("Unique inputs: $uniqueInputs")

        val view = View(Q, Delta, qI, F, uniqueInputs)
        val model = Model(Q, Sigma, Gamma, Delta, qI, F)

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