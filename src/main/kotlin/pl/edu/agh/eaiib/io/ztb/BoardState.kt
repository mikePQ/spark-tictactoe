package pl.edu.agh.eaiib.io.ztb

import java.util.ArrayList

class BoardState(private val boardState: Array<Array<State>>, val nextTurn: State) {
    fun hasGameEnded(): Boolean {
        val statesPerColumn: MutableMap<Int, MutableSet<State>> = HashMap()
        val statesPerFirstDiagonal: MutableSet<State> = HashSet()
        val statesPerSecondDiagonal: MutableSet<State> = HashSet()

        for (rowIndex in 0 until this.boardState.size) {
            val innerArray: Array<State> = this.boardState[rowIndex]
            val statesInRow: MutableSet<State> = HashSet()

            for (columnIndex in 0 until innerArray.size) {
                val currentState: State = innerArray[columnIndex]

                // Row check
                statesInRow.add(currentState)

                // Column check
                if (!statesPerColumn.containsKey(columnIndex)) {
                    statesPerColumn.put(columnIndex, hashSetOf(currentState))
                } else {
                    statesPerColumn[columnIndex]!!.add(currentState)
                }

                // Diagonal check
                if (rowIndex == columnIndex) {
                    statesPerFirstDiagonal.add(currentState)
                }
                if (rowIndex + columnIndex == (this.boardState.size - 1)) {
                    statesPerSecondDiagonal.add(currentState)
                }
            }

            if (statesInRow.size < 2) {
                return true
            }
        }

        if (statesPerColumn.any { it.value.size < 2 }) {
            return true
        }

        if (statesPerFirstDiagonal.size < 2 || statesPerSecondDiagonal.size < 2) {
            return true
        }

        return false
    }

    fun generateChildrenBoardStates(): Array<BoardState> {
        val childrenBoardStates: ArrayList<BoardState> = ArrayList()
        for (rowIndex in 0 until this.boardState.size) {
            val innerArray: Array<State> = this.boardState[rowIndex]
            for (columnIndex in 0 until innerArray.size) {
                if (innerArray[columnIndex] != State.Untouched) {
                    continue
                }
                val boardState: Array<Array<State>> = this.boardState.clone().map { it.clone() }.toTypedArray()
                boardState[rowIndex][columnIndex] = this.nextTurn
                val nextTurn: State = if (this.nextTurn == State.Cross) State.Circle else State.Cross
                childrenBoardStates.add(BoardState(boardState, nextTurn))
            }
        }
        return childrenBoardStates.toTypedArray()
    }

    fun getBoardStateMatrix(): Array<Array<State>> {
        return this.boardState
    }
}