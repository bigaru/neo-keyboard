package `in`.abaddon.neokeyboard

sealed class KeyType
data class CHAR(val char: Char): KeyType()
data class MODIFIER(val mod: MOD): KeyType()
data class CONTROL(val command: Char): KeyType()

enum class MOD(val text: String) {
    SHIFT("⇧"),
    M3("M3"),
    M4("M4"),
    ENTER("↵"),
    BACKSPACE("⇦"),
}
