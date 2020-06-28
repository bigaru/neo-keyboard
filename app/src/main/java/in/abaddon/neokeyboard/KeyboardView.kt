package `in`.abaddon.neokeyboard

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import trikita.anvil.DSL.*
import trikita.anvil.RenderableView

class KeyboardView(val ctx: Context, var ic: InputConnection): RenderableView(ctx) {
    var state = State(Pair(false, false), Pair(false, false), Pair(false, false))
    var editorInfo: EditorInfo? = null

    fun resetNonPermas(){
        if (!state.shift.second) state = state.copy(shift = Pair(false, false))
        if (!state.modifier3.second) state = state.copy(modifier3 = Pair(false, false))
        if (!state.modifier4.second) state = state.copy(modifier4 = Pair(false, false))
    }

    fun onKeyClick(key: KeyType) {
        performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

        when(key) {
            is CHAR -> {
                ic.commitText(key.char.toString(), 1)
                resetNonPermas()
            }

            is SPACE -> {
                ic.commitText(" ", 1)
                resetNonPermas()
            }

            is BACKSPACE -> {
                ic.deleteSurroundingText(1, 0)
                resetNonPermas()
            }

            is SHIFT ->
                state = state.copy(shift = Pair(true, false))

            is M3 ->
                state = state.copy(modifier3 = Pair(true, false))

            is M4 ->
                state = state.copy(modifier4 = Pair(true, false))

            is ENTER ->
                handleEnter()
        }
    }

    private fun handleEnter(){
        if(editorInfo != null) {

            when (editorInfo!!.imeOptions and (EditorInfo.IME_MASK_ACTION or EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
                EditorInfo.IME_ACTION_GO -> ic.performEditorAction(EditorInfo.IME_ACTION_GO)
                EditorInfo.IME_ACTION_NEXT -> ic.performEditorAction(EditorInfo.IME_ACTION_NEXT)
                EditorInfo.IME_ACTION_SEARCH -> ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH)
                EditorInfo.IME_ACTION_SEND -> ic.performEditorAction(EditorInfo.IME_ACTION_SEND)
                else -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            }

        }
    }

    private fun textBasedOnType(key: KeyType) = when(key) {
        is CHAR -> text(key.char.toString())
        is SHIFT -> text("⇧")
        is M3 -> text("M3")
        is M4 -> text("M4")
        is SPACE -> text(" ")
        is BACKSPACE -> text("⇦")
        is ENTER -> text("↵")

        is CONTROL -> text(key.command)
    }

    private fun styledKey(key: KeyType) =
        button{
            size(0, WRAP)

            when(key) {
                is SPACE -> weight(3f)
                else -> weight(1f)
            }

            minimumHeight(dip(0))
            minimumWidth(dip(0))
            minHeight(0)
            minWidth(0)

            when(key) {
                is SPACE -> background(ctx.getDrawable(R.drawable.bordered_key))
                is CHAR -> background(ctx.getDrawable(R.drawable.bordered_key))
                is CONTROL -> background(ctx.getDrawable(R.drawable.bordered_key))
                else -> background(ctx.getDrawable(R.drawable.borderless_key))
            }

            textColor(ctx.getColor(R.color.white))
            textSize(sip(22f))

            allCaps(false)
            typeface(ResourcesCompat.getFont(ctx, R.font.lin_biolinum_rah))

            textBasedOnType(key)
            onClick { v -> onKeyClick(key) }
        }

    fun chosenLayer() = when{
        state.isLayer1() -> Keys.layer1
        state.isLayer2() -> Keys.layer2
        state.isLayer3() -> Keys.layer3
        state.isLayer4() -> Keys.layer4
        state.isLayer5() -> Keys.layer5
        state.isLayer6() -> Keys.layer6
        else -> Keys.layer1
    }

    override fun view() {
        linearLayout {
            size(FILL, WRAP)
            orientation(LinearLayout.VERTICAL)
            padding(dip(2))
            backgroundColor(ctx.getColor(R.color.darkGrey))

            chosenLayer().map{ row ->

                linearLayout{
                    size(FILL, WRAP)
                    orientation(LinearLayout.HORIZONTAL)

                    row.map(this::styledKey)
                }

            }
        }
    }

}
