package `in`.abaddon.neokeyboard

data class State(var SHIFT: ModifierState, var MOD3: ModifierState, var MOD4: ModifierState){
    fun isLayer1() = !SHIFT.isSet() && !MOD3.isSet() && !MOD4.isSet()
    fun isLayer2() = SHIFT.isSet() && !MOD3.isSet() && !MOD4.isSet()
    fun isLayer3() = !SHIFT.isSet() && MOD3.isSet() && !MOD4.isSet()
    fun isLayer4() = !SHIFT.isSet() && !MOD3.isSet() && MOD4.isSet()
    fun isLayer5() = SHIFT.isSet() && MOD3.isSet() && !MOD4.isSet()
    fun isLayer6() = !SHIFT.isSet() && MOD3.isSet() && MOD4.isSet()
}
