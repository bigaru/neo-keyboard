package `in`.abaddon.neokeyboard

import android.inputmethodservice.InputMethodService
import android.view.View

class NeoIME : InputMethodService() {

    override fun onCreateInputView(): View {
        return KeyboardView(this, {currentInputConnection})
    }
}