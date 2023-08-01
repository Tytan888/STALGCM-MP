import java.util.LinkedList

class State (name:String, transitions: LinkedList<Transition>, isStart: Boolean, isFinal: Boolean){
    private val name: String = name
    private val transitions: LinkedList<Transition> = transitions
    private val isStart: Boolean = isStart
    private val isFinal: Boolean =  isFinal


    fun getName(): String{
        return name
    }
    fun getTransitions(): LinkedList<Transition>{
        return transitions
    }

    fun getIsStart(): Boolean{
        return isStart
    }
    fun getIsFinal(): Boolean{
        return isFinal
    }

}