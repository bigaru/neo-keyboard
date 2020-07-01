package `in`.abaddon.neokeyboard

enum class ModifierState{
    UNSET ,
    SET_ONE_TIME,
    SET_PERMA
}

operator fun ModifierState.inc(): ModifierState {
    val newPos = (this.ordinal+1) % ModifierState.values().size
    return ModifierState.values()[newPos]
}

fun ModifierState.isSet(): Boolean = this == ModifierState.SET_ONE_TIME || this == ModifierState.SET_PERMA
