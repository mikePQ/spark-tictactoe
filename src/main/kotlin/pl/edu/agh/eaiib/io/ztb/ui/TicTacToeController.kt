package pl.edu.agh.eaiib.io.ztb.ui

import pl.edu.agh.eaiib.io.ztb.State

class TicTacToeController {
    var boardSize = BoardSize.Normal
    var currentPlayerIsCross = true
    private var fields = createFields(boardSize)

    fun changeBoardSize(boardSize: BoardSize) {
        this.boardSize = boardSize
        fields = createFields(boardSize)
    }

    fun getText(row: Int, column: Int): String {
        return when (fields[row][column]) {
            State.Untouched -> ""
            State.Cross -> "x"
            State.Circle -> "o"
        }
    }

    fun handleClick(row: Int, column: Int) {
        if (fields[row][column] != State.Untouched) {
            return
        }

        fields[row][column] = if (currentPlayerIsCross) State.Cross else State.Circle
        currentPlayerIsCross = !currentPlayerIsCross
    }

    fun clearBoard() {
        fields = createFields(boardSize)
        currentPlayerIsCross = true
    }

    private fun createFields(boardSize: BoardSize): Array<Array<State>> {
        return Array(boardSize.rows, { Array(boardSize.rows, { State.Untouched }) })
    }
}