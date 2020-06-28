package `in`.abaddon.neokeyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo

class NeoIME : InputMethodService() {
    lateinit var keyboardView: KeyboardView

    override fun onCreateInputView(): View {
        keyboardView = KeyboardView(this, currentInputConnection)
        return keyboardView
    }

    override fun onStartInputView(info: EditorInfo, restarting: Boolean) {
        super.onStartInputView(info, restarting)

        val ic = currentInputConnection
        if(ic != null) keyboardView.ic = ic

        keyboardView.editorInfo = info
    }
}
