package pl.edu.agh.eaiib.io.ztb

import org.apache.spark.api.java.JavaRDD
import pl.edu.agh.eaiib.io.ztb.config.SparkConfig
import java.io.Serializable

class Tree(boardSize: Int, sparkConfig: SparkConfig) : Serializable {

    @Transient
    private val sparkContext = sparkConfig.createSparkContext()
    private val roots: JavaRDD<Node> = buildRoots(boardSize)

    private fun buildRoots(boardSize: Int): JavaRDD<Node> {
        val maxIndex = boardSize - 1
        val roots = ArrayList<Node>()
        for (i in 0..maxIndex) {
            (0..maxIndex).mapTo(roots) { Node(emptyBoard(boardSize)) }
        }

        return sparkContext.parallelize(roots)
    }

    private fun buildChildren(node: Node): Iterator<Node> {
        return node.boardState.generateChildrenBoardStates()
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
    private val leaf: Boolean by lazy {
        boardState.hasGameEnded()
    }

    constructor(state: BoardState, parent: Node) : this(state) {
        this.parent = parent
    }

    fun isLeaf(): Boolean {
        return leaf
    }
}

enum class State {
    Untouched, Cross, Circle
}