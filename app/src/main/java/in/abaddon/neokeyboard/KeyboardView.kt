package `in`.abaddon.neokeyboard

import android.content.Context
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import trikita.anvil.DSL.*
import trikita.anvil.RenderableView

sealed class KeyType
data class CHAR(val char: Char): KeyType()
object SHIFT: KeyType()
object M3: KeyType()
object M4: KeyType()
object SPACE: KeyType()
object BACKSPACE: KeyType()
object ENTER: KeyType()

/*
'1',  '2',  '3',  '4',  '5',        '6',  '7',  '8',  '9',  '0',  '-'
'x',  'v',  'l',  'c',  'w',        'k',  'h',  'g',  'f',  'q',  'ß'
'u',  'i',  'a',  'e',  'o',        's',  'n',  'r',  't',  'd',  'y'
'ü',  'ö',  'ä',  'p',  'z',       'b',  'm',  ',',  '.',  'j'    <--
SHIFT M3              SPACE   M4  ENTER
*/

object Keys {
    val lastRow = listOf(SHIFT, M3, SPACE , M4, ENTER)

    val layer1: List<List<KeyType>> = listOf(
        listOf('1',  '2',  '3',  '4',  '5',  '6',  '7',  '8',  '9',  '0',  '-').map{CHAR(it)},
        listOf('x',  'v',  'l',  'c',  'w',  'k',  'h',  'g',  'f',  'q',  'ß').map{CHAR(it)},
        listOf('u',  'i',  'a',  'e',  'o',  's',  'n',  'r',  't',  'd',  'y').map{CHAR(it)},
        listOf('ü',  'ö',  'ä',  'p',  'z',  'b',  'm',  ',',  '.',  'j').map{CHAR(it)} + listOf(BACKSPACE),
        lastRow
    )

    val layer2: List<List<KeyType>> = listOf(
        listOf('°',  '§',  'ℓ',  '»',  '«',  '$',  '€',  '„',  '“',  '”',  '—').map{CHAR(it)},
        listOf('X',  'V',  'L',  'C',  'W',  'K',  'H',  'G',  'F',  'Q',  'ẞ').map{CHAR(it)},
        listOf('U',  'I',  'A',  'E',  'O',  'S',  'N',  'R',  'T',  'D',  'Y').map{CHAR(it)},
        listOf('Ü',  'Ö',  'Ä',  'P',  'Z',  'B',  'M',  '–',  '•',  'J').map{CHAR(it)} + listOf(BACKSPACE),
        lastRow
    )

    val layer3: List<List<KeyType>> = listOf(
        listOf('¹',  '²',  '³',  '›',  '‹',  '¢',  '¥',  '‚',  '‘',  '’',  '-').map{CHAR(it)},
        listOf('…',  '_',  '[',  ']',  '^',  '!',  '<',  '>',  '=',  '&',  'ſ').map{CHAR(it)},
        listOf('\\',  '/',  '{',  '}',  '*', '?',  '(',  ')',  '-',  ':',  '@').map{CHAR(it)},
        listOf('#',  '$',  '|', '~',  '`',  '+',  '%',  '"',  '\'',  ';').map{CHAR(it)} + listOf(BACKSPACE),
        lastRow
    )
}

data class State(
                      // isActive, isPerma
    val shift: Pair<Boolean, Boolean>,
    val modifier3: Pair<Boolean, Boolean>,
    val modifier4: Pair<Boolean, Boolean>
){
    fun isLayer1() = !shift.first && !modifier3.first && !modifier4.first
    fun isLayer2() = shift.first && !modifier3.first && !modifier4.first
    fun isLayer3() = !shift.first && modifier3.first && !modifier4.first
    fun isLayer4() = !shift.first && !modifier3.first && modifier4.first
    fun isLayer5() = shift.first && modifier3.first && !modifier4.first
    fun isLayer6() = !shift.first && modifier3.first && modifier4.first
}

class KeyboardView(val ctx: Context, val ic: () -> InputConnection): RenderableView(ctx) {
    var state = State(Pair(false, false), Pair(false, false), Pair(false, false))

    fun resetNonPermas(){
        if (!state.shift.second) state = state.copy(shift = Pair(false, false))
        if (!state.modifier3.second) state = state.copy(modifier3 = Pair(false, false))
        if (!state.modifier4.second) state = state.copy(modifier4 = Pair(false, false))
    }

    fun onKeyClick(key: KeyType) {
        when(key) {
            is CHAR -> {
                ic().commitText(key.char.toString(), 1)
                resetNonPermas()
            }

            is SPACE -> {
                ic().commitText(" ", 1)
                resetNonPermas()
            }

            is BACKSPACE -> {
                ic().deleteSurroundingText(1, 0)
                resetNonPermas()
            }

            is SHIFT ->
                state = state.copy(shift = Pair(true, false))

            is M3 ->
                state = state.copy(modifier3 = Pair(true, false))
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

            background(ctx.getDrawable(R.drawable.key_style))
            padding(dip(6), dip(12) )
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
        else -> Keys.layer1
    }

    override fun view() {
        linearLayout {
            size(FILL, WRAP)
            orientation(LinearLayout.VERTICAL)

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