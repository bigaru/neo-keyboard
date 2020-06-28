package `in`.abaddon.neokeyboard

sealed class KeyType
data class CHAR(val char: Char): KeyType()
object SHIFT: KeyType()
object M3: KeyType()
object M4: KeyType()
object SPACE: KeyType()
object BACKSPACE: KeyType()
object ENTER: KeyType()
data class CONTROL(val command: String): KeyType()
