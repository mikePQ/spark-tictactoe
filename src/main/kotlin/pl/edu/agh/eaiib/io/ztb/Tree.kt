package pl.edu.agh.eaiib.io.ztb

import org.apache.spark.api.java.JavaRDD
import pl.edu.agh.eaiib.io.ztb.config.SparkConfig
import java.io.Serializable

class Tree(boardState: BoardState, sparkConfig: SparkConfig) : Serializable {

    @Transient
    private val sparkContext = sparkConfig.sparkContext
    private val roots: JavaRDD<Node> = buildRoots(boardState)

    private fun buildRoots(boardState: BoardState): JavaRDD<Node> {
        val roots = mutableListOf(Node(boardState))
        return sparkContext.parallelize(roots)
    }

    private fun buildChildren(node: Node): Iterator<Node> {
        val states = node.boardState.generateChildrenBoardStates()

        return states
                .asSequence()
                .map { boardState -> Node(boardState, node) }
                .iterator()
    }

    fun collectAllNodes(): JavaRDD<Node> {
        var elements = roots
        var prevCount = elements.count()

        while (true) {
            elements = elements.flatMap {
                if (it.isLeaf()) it.iterator() else buildChildren(it)
            }

            val count = elements.count()
            if (count == prevCount) {
                return elements
            }
            prevCount = count
        }
    }

    private fun <T> T.iterator(): Iterator<T> {
        return listOf(this).iterator()
    }

    private fun emptyBoard(boardSize: Int): BoardState {
        val array = array2d(boardSize, boardSize, { State.Untouched })
        return BoardState(array, State.Cross)
    }
}

data class Node(val boardState: BoardState) : Serializable {

    var parent: Node? = null
    val result: Result by lazy {
        boardState.getResult()
    }

    constructor(state: BoardState, parent: Node) : this(state) {
        this.parent = parent
    }

    fun isLeaf(): Boolean {
        return result != Result.NotFinished
    }
}

enum class State {
    Untouched, Cross, Circle
}

enum class Result {
    NotFinished, CircleWon, CrossWon, Draw
}