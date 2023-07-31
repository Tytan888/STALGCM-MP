class State (name:String, transitions: List<Transition>, isStart: Boolean, isFinal: Boolean){
    private val name: String = name
    private val transitions: Array<Transition> = transitions
    private val isStart: Boolean = isStart
    private val isFinal: Boolean =  isFinal

    fun getTransitions(): Array<Transition>{
        return transitions
    }

    fun getIsStart(): Boolean{
        return isStart
    }
    fun getIsFinal(): Boolean{
        return isFinal
    }

}