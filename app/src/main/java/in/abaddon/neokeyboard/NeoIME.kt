package `in`.abaddon.neokeyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo

interface IMEService{
    fun insertChar(char: Char)
    fun removePrevChar()
    fun handleEnter()
}

class NeoIME : InputMethodService(), IMEService{
    lateinit var editorInfo: EditorInfo

    lateinit var keyboardView: DefaultKeyboardView
    lateinit var keyboardPresenter: KeyboardPresenter

    override fun onCreateInputView(): View {
        keyboardView = DefaultKeyboardView(this)
        keyboardPresenter = DefaultKeyboardPresenter(keyboardView, this)
        return keyboardView
    }

    override fun onStartInputView(info: EditorInfo, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        editorInfo = info
    }

    override fun insertChar(char: Char) {
        currentInputConnection.commitText(char.toString(), 1)
    }

    override fun removePrevChar(){
        currentInputConnection.deleteSurroundingText(1, 0)
    }

    override fun handleEnter() {
        when (editorInfo.imeOptions and (EditorInfo.IME_MASK_ACTION or EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
            EditorInfo.IME_ACTION_GO -> currentInputConnection.performEditorAction(EditorInfo.IME_ACTION_GO)
            EditorInfo.IME_ACTION_NEXT -> currentInputConnection.performEditorAction(EditorInfo.IME_ACTION_NEXT)
            EditorInfo.IME_ACTION_SEARCH -> currentInputConnection.performEditorAction(EditorInfo.IME_ACTION_SEARCH)
            EditorInfo.IME_ACTION_SEND -> currentInputConnection.performEditorAction(EditorInfo.IME_ACTION_SEND)
            else -> currentInputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
        }
    }

}
