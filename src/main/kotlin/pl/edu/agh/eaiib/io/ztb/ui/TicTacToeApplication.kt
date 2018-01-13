package pl.edu.agh.eaiib.io.ztb.ui

import tornadofx.*

class TicTacToeApplication : App(TicTacToeView::class, Styles::class)

enum class BoardSize(val rows: Int) {
    Normal(3), Enlarged(4)
}