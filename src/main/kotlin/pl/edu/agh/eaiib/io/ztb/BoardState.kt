package pl.edu.agh.eaiib.io.ztb

import java.io.Serializable
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicInteger

class BoardState(private val boardState: Array<Array<State>>, val nextTurn: State) : Serializable {

    fun getResult(): Result {
        val statesPerColumn: MutableMap<Int, MutableSet<State>> = HashMap()
        val statesPerFirstDiagonal: MutableSet<State> = HashSet()
        val statesPerSecondDiagonal: MutableSet<State> = HashSet()

        val untouchedCounter = AtomicInteger(0)
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

                if (currentState == State.Untouched) {
                    untouchedCounter.incrementAndGet()
                }
            }

            if (isGameFinished(statesInRow)) {
                return gameWon(statesInRow)
            }
        }

        val perColumn = statesPerColumn.asIterable().firstOrNull { isGameFinished(it.value) }
        if (perColumn != null) {
            return gameWon(perColumn.value)
        }

        if (isGameFinished(statesPerFirstDiagonal)) {
            return gameWon(statesPerFirstDiagonal)
        }

        if (isGameFinished(statesPerSecondDiagonal)) {
            return gameWon(statesPerSecondDiagonal)
        }

        if (untouchedCounter.get() == 0) {
            return Result.Draw
        }

        return Result.NotFinished
    }

    private fun gameWon(states: Set<State>): Result {
        return if (states.first() == State.Cross) Result.CrossWon else Result.CircleWon
    }

    private fun isGameFinished(states: Set<State>): Boolean {
        return states.size < 2 && !states.contains(State.Untouched)
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