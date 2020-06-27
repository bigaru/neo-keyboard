package `in`.abaddon.neokeyboard

import android.content.Context
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import trikita.anvil.DSL.*
import trikita.anvil.RenderableView

object Keys {
    val layer1 = listOf(
        listOf('1',  '2',  '3',  '4',  '5',  '6',  '7',  '8',  '9',  '0',  '-'),
        listOf('x',  'v',  'l',  'c',  'w',  'k',  'h',  'g',  'f',  'q',  'ß'),
        listOf('u',  'i',  'a',  'e',  'o',  's',  'n',  'r',  't',  'd',  'y'),
        listOf('ü',  'ö',  'ä',  'p',  'z',  'b',  'm',  ',',  '.',  'j')
    )
}

class KeyboardView(val ctx: Context, val enterChar:(Char) -> Unit): RenderableView(ctx) {

    fun onKeyClick(c: Char) {
        enterChar(c)
    }

    private fun styledKey(content: Char) =
        button{
            size(0, WRAP)
            weight(1f)

            minimumHeight(dip(0))
            minimumWidth(dip(0))
            minHeight(0)
            minWidth(0)

            background(ctx.getDrawable(R.drawable.key_style))
            padding(dip(6))
            textSize(sip(22f))

            allCaps(false)
            typeface(ResourcesCompat.getFont(ctx, R.font.lin_biolinum_rah))

            text(content.toString())
            onClick { v -> onKeyClick(content) }
        }

    override fun view() {
        linearLayout {
            size(FILL, WRAP)
            orientation(LinearLayout.VERTICAL)

            Keys.layer1.map{ row ->

                linearLayout{
                    size(FILL, WRAP)
                    orientation(LinearLayout.HORIZONTAL)

                    row.map(this::styledKey)
                }

            }
        }
    }

}