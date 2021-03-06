package pl.edu.agh.eaiib.io.ztb

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BoardStateTest {

    @Test
    fun hasGameEndedDiagonalCircle() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched)
        val row2: Array<State> = arrayOf(State.Untouched, State.Circle, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CircleWon, boardState.getResult())
    }

    @Test
    fun hasGameEndedDiagonalCross() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Cross)
        val row2: Array<State> = arrayOf(State.Untouched, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CrossWon, boardState.getResult())
    }

    @Test
    fun hasGameNotEndedDiagonal() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched)
        val row2: Array<State> = arrayOf(State.Untouched, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.NotFinished, boardState.getResult())
    }

    @Test
    fun hasGameNotEndedClear() {
        val row1: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Untouched)
        val row2: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Untouched)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.NotFinished, boardState.getResult())
    }

    @Test
    fun hasGameEndedColumn() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched)
        val row2: Array<State> = arrayOf(State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Circle, State.Untouched, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CircleWon, boardState.getResult())
    }

    @Test
    fun hasGameNotEndedColumn() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched)
        val row2: Array<State> = arrayOf(State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.NotFinished, boardState.getResult())
    }

    @Test
    fun hasGameEndedRow() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched)
        val row2: Array<State> = arrayOf(State.Cross, State.Cross, State.Cross)
        val row3: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CrossWon, boardState.getResult())
    }

    @Test
    fun hasGameEndedDiagonalSize4() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched, State.Untouched)
        val row2: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Circle, State.Untouched)
        val row4: Array<State> = arrayOf(State.Untouched, State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3, row4)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CircleWon, boardState.getResult())
    }

    @Test
    fun hasGameEndedSecondDiagonalSize4() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched, State.Cross)
        val row2: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Cross, State.Cross, State.Untouched)
        val row4: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3, row4)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CrossWon, boardState.getResult())
    }

    @Test
    fun hasGameNotEndedSecondDiagonalSize4() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Untouched, State.Cross)
        val row2: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Circle, State.Cross, State.Untouched)
        val row4: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3, row4)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.NotFinished, boardState.getResult())
    }

    @Test
    fun hasGameEndedRowSize4() {
        val row1: Array<State> = arrayOf(State.Cross, State.Untouched, State.Untouched, State.Cross)
        val row2: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row4: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3, row4)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CrossWon, boardState.getResult())
    }

    @Test
    fun hasGameNotEndedRowSize4() {
        val row1: Array<State> = arrayOf(State.Cross, State.Untouched, State.Untouched, State.Cross)
        val row2: Array<State> = arrayOf(State.Circle, State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row4: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3, row4)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.NotFinished, boardState.getResult())
    }

    @Test
    fun hasGameEndedColumn4() {
        val row1: Array<State> = arrayOf(State.Circle, State.Circle, State.Circle, State.Circle)
        val row2: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Circle, State.Cross, State.Untouched)
        val row4: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3, row4)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.CircleWon, boardState.getResult())
    }

    @Test
    fun hasGameNotEndedColumn4() {
        val row1: Array<State> = arrayOf(State.Circle, State.Cross, State.Circle, State.Circle)
        val row2: Array<State> = arrayOf(State.Cross, State.Circle, State.Cross, State.Untouched)
        val row3: Array<State> = arrayOf(State.Untouched, State.Circle, State.Cross, State.Untouched)
        val row4: Array<State> = arrayOf(State.Cross, State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3, row4)
        val boardState = BoardState(input, State.Cross)
        assertEquals(Result.NotFinished, boardState.getResult())
    }

    @Test
    fun generateChildrenBoardStates() {
        val row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Circle)
        val row2: Array<State> = arrayOf(State.Circle, State.Circle, State.Circle)
        val row3: Array<State> = arrayOf(State.Untouched, State.Circle, State.Circle)
        val input: Array<Array<State>> = arrayOf(row1, row2, row3)
        val boardState = BoardState(input, State.Cross)
        val output: Array<BoardState> = boardState.generateChildrenBoardStates()
        assertEquals(2, output.size)

        val expectedOutput1_row1: Array<State> = arrayOf(State.Circle, State.Cross, State.Circle)
        val expectedOutput1_row2: Array<State> = arrayOf(State.Circle, State.Circle, State.Circle)
        val expectedOutput1_row3: Array<State> = arrayOf(State.Untouched, State.Circle, State.Circle)
        val expectedOutput1_input: Array<Array<State>> = arrayOf(expectedOutput1_row1, expectedOutput1_row2, expectedOutput1_row3)
        val expectedBoardState1 = BoardState(expectedOutput1_input, State.Circle)
        assertArrayEquals(expectedBoardState1.getBoardStateMatrix(), output[0].getBoardStateMatrix())
        assertEquals(State.Circle, output[0].nextTurn)

        val expectedOutput2_row1: Array<State> = arrayOf(State.Circle, State.Untouched, State.Circle)
        val expectedOutput2_row2: Array<State> = arrayOf(State.Circle, State.Circle, State.Circle)
        val expectedOutput2_row3: Array<State> = arrayOf(State.Cross, State.Circle, State.Circle)
        val expectedOutput2_input: Array<Array<State>> = arrayOf(expectedOutput2_row1, expectedOutput2_row2, expectedOutput2_row3)
        val expectedBoardState2 = BoardState(expectedOutput2_input, State.Circle)
        assertArrayEquals(expectedBoardState2.getBoardStateMatrix(), output[1].getBoardStateMatrix())
        assertEquals(State.Circle, output[1].nextTurn)
    }
}