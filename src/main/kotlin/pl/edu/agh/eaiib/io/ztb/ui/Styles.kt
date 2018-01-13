package pl.edu.agh.eaiib.io.ztb.ui

import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val board by cssclass()
        val boardCell by cssclass()
        val boardText by cssclass()

        val fieldColor = c("#ffffff")
        val separatorColor = c("#aaaaaa")
        val hoverColor = c("#d49942")
    }

    init {
        board {
            backgroundColor.elements.clear()
            backgroundColor += separatorColor
        }

        boardCell {
            fill = fieldColor
        }

        boardText {
            textFill = hoverColor
        }
    }
}