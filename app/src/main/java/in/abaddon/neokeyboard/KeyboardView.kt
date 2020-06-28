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
    var modifierFlags = 0
    var editorInfo: EditorInfo? = null

    fun resetNonPermas(){
        modifierFlags = Modifier.resetModifiers(modifierFlags)
    }

    fun doubleTap(modifier: Int, modifierPerma: Int){
        if(modifierFlags and modifier == modifier){
            modifierFlags = modifierFlags and modifier.inv()
            modifierFlags = modifierFlags or modifierPerma
        }
        else if(modifierFlags and modifierPerma == modifierPerma){
            modifierFlags = modifierFlags and modifierPerma.inv()
        }
        else {
            modifierFlags = modifierFlags or modifier
        }
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

            is SHIFT -> {
                doubleTap(Modifier.SHIFT, Modifier.SHIFT_PERMA)
            }

            is M3 -> {
                doubleTap(Modifier.MOD3, Modifier.MOD3_PERMA)
            }

            is M4 -> {
                doubleTap(Modifier.MOD4, Modifier.MOD4_PERMA)
            }

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

    fun chosenLayer()= when{
        Modifier.isLayer6(modifierFlags) -> Keys.layer6
        Modifier.isLayer5(modifierFlags) -> Keys.layer5
        Modifier.isLayer4(modifierFlags) -> Keys.layer4
        Modifier.isLayer3(modifierFlags) -> Keys.layer3
        Modifier.isLayer2(modifierFlags) -> Keys.layer2
        Modifier.isLayer1(modifierFlags) -> Keys.layer1
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
