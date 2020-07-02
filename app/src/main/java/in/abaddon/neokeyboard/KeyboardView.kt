package `in`.abaddon.neokeyboard

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.HapticFeedbackConstants
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import trikita.anvil.DSL.*
import trikita.anvil.RenderableView

class KeyboardView(val ctx: Context, var ic: InputConnection): RenderableView(ctx) {
    val state = State(ModifierState.UNSET, ModifierState.UNSET, ModifierState.UNSET)
    var editorInfo: EditorInfo? = null

    fun resetNonPermas() {
        if (state.SHIFT == ModifierState.SET_ONE_TIME) state.SHIFT  = ModifierState.UNSET
        if (state.MOD3 == ModifierState.SET_ONE_TIME) state.MOD3 = ModifierState.UNSET
        if (state.MOD4 == ModifierState.SET_ONE_TIME) state.MOD4 = ModifierState.UNSET
    }

    val onKeyClick: (KeyType) -> ((View) -> Unit) = {key: KeyType -> { v:View ->
        performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

        when{
            key == CHAR(' ') -> {
                ic.commitText(" ", 1)
                resetNonPermas()
            }

            key is CHAR -> {
                ic.commitText(key.char.toString(), 1)
                resetNonPermas()
            }

            key is MODIFIER && key.mod == MOD.BACKSPACE -> {
                ic.deleteSurroundingText(1, 0)
                resetNonPermas()
            }

            key is MODIFIER && key.mod == MOD.SHIFT -> state.SHIFT++

            key is MODIFIER && key.mod == MOD.M3 -> state.MOD3++

            key is MODIFIER && key.mod == MOD.M4-> state.MOD4++

            key is MODIFIER && key.mod == MOD.ENTER -> handleEnter()
        }
    }}

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

    private fun keyBasedOnType(key: KeyType) {
        val clickHandler = onKeyClick(key)

        when {
            key is CHAR && key.char == ' ' ->  styledKey(key.char.toString(), clickHandler, ctx.getDrawable(R.drawable.bordered_key), layoutWeight = 3f)
            key is CHAR ->  styledKey(key.char.toString(), clickHandler)
            key is MODIFIER ->  styledKey(key.mod.text, clickHandler)
            key is CONTROL -> styledKey(key.command.toString(), clickHandler)
        }
    }

    private fun styledKey(
        textContent: String,
        clickHandler: (View) -> Unit,
        btnBackground: Drawable? = ctx.getDrawable(R.drawable.borderless_key),
        layoutWeight: Float = 1f
    ){
        button{
            size(0, WRAP)
            weight(layoutWeight)

            minimumHeight(dip(0))
            minimumWidth(dip(0))
            minHeight(0)
            minWidth(0)

            background(btnBackground)
            textColor(ctx.getColor(R.color.white))
            textSize(sip(26f))
            padding(dip(0), dip(6))

            allCaps(false)
            typeface(ResourcesCompat.getFont(ctx, R.font.dejavu_light))

            text(textContent)
            onClick(clickHandler)
        }
    }

    fun chosenLayer()= when{
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

                    row.map(this::keyBasedOnType)
                }

            }
        }
    }

}
