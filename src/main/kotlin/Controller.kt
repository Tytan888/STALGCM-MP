import java.io.BufferedReader
import java.io.File
import java.util.LinkedList
import javax.swing.Timer

class Controller {

    init {
        val bufferedReader: BufferedReader = File(Constants.FILE_NAME).bufferedReader()

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

        val view = View(Q, Delta, qI, F, uniqueInputs)
        val model = Model(Q, Sigma, Gamma, Delta, qI, F)

        model.initializeMachine()

        var inputString = "temp"
        var i = 0
        var started = false
        var status = -1
        var valid = false
        var finalState: State? = null

        view.setLeftButtonActionListener {
            if (!started) {
                inputString = view.getInputFieldText()
                view.turnInputFieldToLabel()
                view.disableRightButton()
                started = true
            } else {

                if (model.getCurrentState()?.getIsFinal() == true && i >= inputString.length && i == 0) {
                    finalState = model.getCurrentState()
                    valid = true
                }

                status = if (i < inputString.length) {
                    model.transitionState(inputString[i].toString())
                } else {
                    model.transitionState("")
                }

                if (status == 1) {
                    i++
                }

                if (model.getCurrentState()?.getIsFinal() == true && i >= inputString.length) {
                    finalState = model.getCurrentState()
                    valid = true
                }

                if (status == 0) {
                    view.disableLeftButton()
                }
            }
            if (status != 0) {
                view.updateDesc(model.getCurrentTransition())
                view.highlightInputLabel(i - 1)
                view.replaceStack(model.getStack())
                view.setHighlightedTransition(model.getCurrentTransition())
            } else {
                println(finalState)
                view.finishedDesc(valid, finalState)
                view.finishTable(valid)
            }
        }
        var timer: Timer? = null
        view.setRightButtonActionListener {
            timer = Timer(Constants.TIMER_DELAY) {
                if (!started) {
                    inputString = view.getInputFieldText()
                    view.turnInputFieldToLabel()
                    view.disableLeftButton()
                    view.disableRightButton()
                    started = true
                } else {

                    if (model.getCurrentState()?.getIsFinal() == true && i >= inputString.length && i == 0) {
                        finalState = model.getCurrentState()
                        valid = true
                    }

                    status = if (i < inputString.length) {
                        model.transitionState(inputString[i].toString())
                    } else {
                        model.transitionState("")
                    }
                    println(model.getCurrentState()?.getName())
                    println(model.getStack())
                    if (status == 1) {
                        i++
                    }
                    if (model.getCurrentState()?.getIsFinal() == true && i >= inputString.length) {
                        finalState = model.getCurrentState()
                        valid = true
                    }

                    if (status == 0) {
                        timer?.stop()
                    }
                }
                if (status != 0) {
                    view.updateDesc(model.getCurrentTransition())
                    view.highlightInputLabel(i - 1)
                    view.replaceStack(model.getStack())
                    view.setHighlightedTransition(model.getCurrentTransition())
                } else {
                    println(finalState)
                    view.finishedDesc(valid, finalState)
                    view.finishTable(valid)
                }
            }
            timer?.start()
        }
    }

}

fun main() {
    Controller()
}