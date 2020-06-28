package `in`.abaddon.neokeyboard

data class State(
    // isActive, isPerma
    val shift: Pair<Boolean, Boolean>,
    val modifier3: Pair<Boolean, Boolean>,
    val modifier4: Pair<Boolean, Boolean>
){
    fun isLayer1() = !shift.first && !modifier3.first && !modifier4.first
    fun isLayer2() = shift.first && !modifier3.first && !modifier4.first
    fun isLayer3() = !shift.first && modifier3.first && !modifier4.first
    fun isLayer4() = !shift.first && !modifier3.first && modifier4.first
    fun isLayer5() = shift.first && modifier3.first && !modifier4.first
    fun isLayer6() = !shift.first && modifier3.first && modifier4.first
}
