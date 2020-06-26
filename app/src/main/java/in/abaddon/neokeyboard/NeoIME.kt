package `in`.abaddon.neokeyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.Button

class NeoIME : InputMethodService(), View.OnClickListener {

    override fun onCreateInputView(): View {

        val keyboardView =  layoutInflater.inflate(R.layout.keyboard_view, null)

        NeoKeys.layer1.forEach{entry ->
            val btn = keyboardView.findViewById<Button>(entry.key)
            btn.setText(entry.value.toString())
            btn.setOnClickListener(this)
        }

        return keyboardView
    }

    override fun onClick(v: View) {
        val ic = currentInputConnection

        if (v is Button) {
            val char = NeoKeys.layer1[v.id]
            ic.commitText(char.toString(), 1)
        }
    }


}

object NeoKeys {
    val layer1 = mapOf (
        R.id.key_a1 to '1', R.id.key_a2 to '2', R.id.key_a3 to '3', R.id.key_a4 to '4', R.id.key_a5 to '5', R.id.key_a6 to '6', R.id.key_a7 to '7', R.id.key_a8 to '8', R.id.key_a9 to '9', R.id.key_a10 to '0', R.id.key_a11 to '-',
                                                                                                                                                                                                                                                                                                                                                    // TODO ß font
        R.id.key_b1 to 'x', R.id.key_b2 to 'v', R.id.key_b3 to 'l', R.id.key_b4 to 'c', R.id.key_b5 to 'w', R.id.key_b6 to 'k', R.id.key_b7 to 'h', R.id.key_b8 to 'g', R.id.key_b9 to 'f', R.id.key_b10 to 'q', R.id.key_b11 to 's',
        R.id.key_c1 to 'u', R.id.key_c2 to 'i', R.id.key_c3 to 'a', R.id.key_c4 to 'e', R.id.key_c5 to 'o', R.id.key_c6 to 's', R.id.key_c7 to 'n', R.id.key_c8 to 'r', R.id.key_c9 to 't', R.id.key_c10 to 'd', R.id.key_c11 to 'y',
        R.id.key_d1 to 'ü', R.id.key_d2 to 'ö', R.id.key_d3 to 'ä', R.id.key_d4 to 'p', R.id.key_d5 to 'z', R.id.key_d6 to 'b', R.id.key_d7 to 'm', R.id.key_d8 to ',', R.id.key_d9 to '.', R.id.key_d10 to 'j'
    )
}