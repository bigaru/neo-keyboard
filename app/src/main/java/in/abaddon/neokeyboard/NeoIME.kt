package `in`.abaddon.neokeyboard

import android.inputmethodservice.InputMethodService
import android.view.View

class NeoIME : InputMethodService() {

    override fun onCreateInputView(): View {
        return KeyboardView(this, this::enterChar)
    }

    fun enterChar(char: Char) {
        val ic = currentInputConnection
        ic.commitText(char.toString(), 1)
    }
}