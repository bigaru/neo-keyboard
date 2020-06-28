package `in`.abaddon.neokeyboard

// TODO Maybe switch to 755
// 000
// 101
// 202
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

    const val SHIFT             = 1
    const val SHIFT_PERMA = 2

    const val MOD3             = 4
    const val MOD3_PERMA = 8

    const val MOD4             = 16
    const val MOD4_PERMA = 32

    fun resetModifiers(flags: Int) = flags and (21.inv())

    fun isLayer1(flags: Int) = flags == 0
    fun isLayer2(flags: Int) = (flags and SHIFT == SHIFT) || (flags and SHIFT_PERMA == SHIFT_PERMA)
    fun isLayer3(flags: Int) = (flags and MOD3 == MOD3) || (flags and MOD3_PERMA == MOD3_PERMA)
    fun isLayer4(flags: Int) = (flags and MOD4 == MOD4) || (flags and MOD4_PERMA == MOD4_PERMA)
    fun isLayer5(flags: Int) = isLayer2(flags) && isLayer3(flags)
    fun isLayer6(flags: Int) = isLayer3(flags) && isLayer4(flags)
}
