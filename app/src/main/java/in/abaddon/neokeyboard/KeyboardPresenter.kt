package `in`.abaddon.neokeyboard

import android.view.View

interface KeyboardPresenter{
    val onKeyClick: (KeyType) -> ((View) -> Unit)
}

class DefaultKeyboardPresenter(val keyboardView: KeyboardView, val imeService: IMEService): KeyboardPresenter{
    val state = State(ModifierState.UNSET, ModifierState.UNSET, ModifierState.UNSET)

    init {
        keyboardView.init(state, this)
    }

    override val onKeyClick: (KeyType) -> ((View) -> Unit) = { key: KeyType -> { v: View ->
        keyboardView.performHapticFeedback()

        when{
            key is CHAR -> {
                imeService.insertChar(key.char)
                resetNonPermas()
            }

            key is MODIFIER && key.mod == MOD.BACKSPACE -> {
                imeService.removePrevChar()
                resetNonPermas()
            }

            key is MODIFIER && key.mod == MOD.SHIFT -> state.SHIFT++

            key is MODIFIER && key.mod == MOD.M3 -> state.MOD3++

            key is MODIFIER && key.mod == MOD.M4-> state.MOD4++

            key is MODIFIER && key.mod == MOD.ENTER -> imeService.handleEnter()
        }
    }}

    fun resetNonPermas() {
        if (state.SHIFT == ModifierState.SET_ONE_TIME) state.SHIFT  = ModifierState.UNSET
        if (state.MOD3 == ModifierState.SET_ONE_TIME) state.MOD3 = ModifierState.UNSET
        if (state.MOD4 == ModifierState.SET_ONE_TIME) state.MOD4 = ModifierState.UNSET
    }
}
