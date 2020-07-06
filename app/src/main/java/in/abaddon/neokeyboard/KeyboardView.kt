package `in`.abaddon.neokeyboard

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import trikita.anvil.DSL.*
import trikita.anvil.RenderableView

interface KeyboardView {
    fun performHapticFeedback()
    fun init(state: State, keyboardPresenter: KeyboardPresenter)
}

class DefaultKeyboardView(val ctx: Context): RenderableView(ctx), KeyboardView {
    lateinit var state: State
    lateinit var keyboardPresenter: KeyboardPresenter

    override fun init(state: State, keyboardPresenter: KeyboardPresenter) {
        this.state = state
        this.keyboardPresenter = keyboardPresenter
    }

    override fun performHapticFeedback() {
        performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }

    private fun getTextColorForMod(key: MODIFIER): Int {
        if(key.mod == MOD.SHIFT && state.SHIFT.isSet()) return ctx.getColor(R.color.lightAccent)
        if(key.mod == MOD.M3 && state.MOD3.isSet()) return ctx.getColor(R.color.lightAccent)
        if(key.mod == MOD.M4 && state.MOD4.isSet()) return ctx.getColor(R.color.lightAccent)
        return ctx.getColor(R.color.white)
    }

    private fun getBackgroundForMod(key: MODIFIER): Drawable? {
        if(key.mod == MOD.SHIFT && state.SHIFT == ModifierState.SET_PERMA) return ctx.getDrawable(R.drawable.active_bordered_bg)
        if(key.mod == MOD.M3 && state.MOD3 == ModifierState.SET_PERMA) return ctx.getDrawable(R.drawable.active_bordered_bg)
        if(key.mod == MOD.M4 && state.MOD4 == ModifierState.SET_PERMA) return ctx.getDrawable(R.drawable.active_bordered_bg)
        return ctx.getDrawable(R.drawable.borderless_key)
    }

    private fun keyBasedOnType(key: KeyType) {
        val clickHandler = keyboardPresenter.onKeyClick(key)

        when {
            key is CHAR && key.char == ' ' ->  styledKey(key.char.toString(), clickHandler, btnBackground =  ctx.getDrawable(R.drawable.bordered_key), layoutWeight = 3f)
            key is CHAR ->  styledKey(key.char.toString(), clickHandler)
            key is MODIFIER -> {
                styledKey(key.mod.text, clickHandler, fontColor = getTextColorForMod(key), btnBackground = getBackgroundForMod(key))
            }
            key is CONTROL -> styledKey(key.command.toString(), clickHandler)
        }
    }

    private fun styledKey(
        textContent: String,
        clickHandler: (View) -> Unit,
        fontColor: Int = ctx.getColor(R.color.white),
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
            textColor(fontColor)
            textSize(sip(26f))
            padding(dip(0), dip(6))

            allCaps(false)
            typeface(ResourcesCompat.getFont(ctx, R.font.dejavu_light))

            text(textContent)
            onClick(clickHandler)
        }
    }

    // TODO move to Presenter
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
