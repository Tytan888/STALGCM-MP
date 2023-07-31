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
        for (i in 0 until carDelta) {
            val line = bufferedReader.readLine().split(" ")
            Delta.add(Transition(line[0], line[1], line[2], line[3], line[4]))
        }
        Delta.sort()

        val qI = bufferedReader.readLine()
        val F = bufferedReader.readLine().split(" ")

        var uniqueInputs = LinkedList<String>()
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

        val view = View()
        val model = Model()
    }

}

fun main(args: Array<String>) {
    Controller();
}