package `in`.abaddon.neokeyboard

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

    val layer4: List<List<KeyType>> = listOf(
        listOf('ª',  'º',  '№',  '·',  '£',  '¤').map{CHAR(it)} + listOf(CONTROL("⇥")) + listOf('/',  '*',  '-').map{CHAR(it)},
        listOf("⇞","⌫","⇡", "⌦", "⇟").map{CONTROL(it)} + listOf('¡', '7', '8', '9', '+', '−').map{CHAR(it)},
        listOf("⇱","⇠","⇣", "⇢", "⇲").map{CONTROL(it)} + listOf('¿', '4', '5', '6', ',', '.').map{CHAR(it)},
        listOf("⌧","⇥", "${9088.toChar()}", "↵", "↶").map{CONTROL(it)} + listOf(':', '1', '2', '3', ';').map{CHAR(it)} + listOf(BACKSPACE),
        lastRow
    )

    val layer5: List<List<KeyType>> = listOf(
        listOf('₁',  '₂',  '₃',  '♀',  '♂',  '⚥',  'ϰ',  '⟨',  '⟩',  '₀',  '‑').map{CHAR(it)},
        listOf('ξ',  'λ',  'χ',  'ω',  'κ',  'ψ',  'γ',  'φ',  'ϕ',  'ς').map{CHAR(it)},
        listOf('ι',  'α',  'ε',  'ο', 'σ',  'ν',  'ρ',  'τ',  'δ',  'υ').map{CHAR(it)},
        listOf('ϵ',  'η', 'π',  'ζ',  'β',  'μ', 'ϱ',  'ϑ',  'θ').map{CHAR(it)} + listOf(BACKSPACE),
        lastRow
    )

    val layer6: List<List<KeyType>> = listOf(
        listOf('¬',  '∨',  '∧',  '⊥',  '∡',  '∥',  '→',  '∞',  '∝',  '∅', '╌').map{CHAR(it)},
        listOf('Ξ',  '√',  'Λ',  'ℂ',  'Ω',  '×',  'Ψ',  'Γ',  'Φ',  'ℚ',  '∘').map{CHAR(it)},
        listOf('⊂',  '∫',  '∀',  '∃',  '∈',  'Σ',  'ℕ',  'ℝ',  '∂',  'Δ',  '∇').map{CHAR(it)},
        listOf('∪',  '∩',  'ℵ',  'Π',  'ℤ',  '⇐',  '⇔',  '⇒',  '↦',  'Θ').map{CHAR(it)} + listOf(BACKSPACE),
        lastRow
    )
}
