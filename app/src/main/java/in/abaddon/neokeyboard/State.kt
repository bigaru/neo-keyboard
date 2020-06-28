package `in`.abaddon.neokeyboard

object Modifier{
    /*
    *  000001   SHIFT                    1
    *  000010   SHIFT PERMA        2
    *
    *  000100   M2                        4
    *  001000   M2 PERMA            8
    *
    *  010000   M3                      16
    *  100000   M3 PERMA          32
    *
    * Setting Flags:        nFlags = nFlags | BIT_FLAG_TEST ;
    * Clearing      :         nFlags = nFlags & ~BIT_FLAG_TEST;
    * Check         :         (nFlags & BIT_FLAG_TEST) == BIT_FLAG_TEST
    */
}

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
