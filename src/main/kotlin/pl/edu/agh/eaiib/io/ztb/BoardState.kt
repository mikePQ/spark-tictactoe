package pl.edu.agh.eaiib.io.ztb

class BoardState(private val boardState : Array<Array<State>>) {
    fun hasGameEnded(): Boolean {
        val statesPerColumn : MutableMap<Int, MutableSet<State>>  = HashMap();
        val statesPerFirstDiagonal: MutableSet<State>  = HashSet();
        val statesPerSecondDiagonal: MutableSet<State>  = HashSet();

        for (rowIndex in 0.. this.boardState.size - 1) {
            val innerArray : Array<State> = this.boardState[rowIndex];
            val statesInRow : MutableSet<State>  = HashSet();

            for (columnIndex in 0.. innerArray.size - 1) {
                val currentState : State = innerArray[columnIndex];

                    // Row check
                    statesInRow.add(currentState);

                    // Column check
                    if (!statesPerColumn.containsKey(columnIndex)) {
                        statesPerColumn.put(columnIndex, hashSetOf(currentState))
                    }
                    else {
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
            if (statesInRow.size < 2)
                return true;
        }
        if (statesPerColumn.any{it.value.size < 2})
            return true;

        if (statesPerFirstDiagonal.size < 2 || statesPerSecondDiagonal.size < 2)
            return true;

        return false;
    }
}