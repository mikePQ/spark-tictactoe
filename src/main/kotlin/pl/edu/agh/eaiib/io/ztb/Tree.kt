package pl.edu.agh.eaiib.io.ztb

import org.apache.spark.api.java.JavaRDD
import pl.edu.agh.eaiib.io.ztb.config.SparkConfig
import java.io.Serializable

class Tree(boardSize: Int, sparkConfig: SparkConfig) {

    private val sparkContext = sparkConfig.createSparkContext()
    private val roots: JavaRDD<Node> = buildRoots(boardSize)

    private fun buildRoots(boardSize: Int): JavaRDD<Node> {
        val maxIndex = boardSize - 1
        val roots = ArrayList<Node>()
        for (i in 0..maxIndex) {
            (0..maxIndex).mapTo(roots) { Node(State.Untouched, i, it) }
        }

        return sparkContext.parallelize(roots)
    }

    private fun buildChildren(node: Node): Iterator<Node> {
        return ArrayList<Node>().iterator()
    }

    private fun collectAllNodes() {
        var elements = roots
        var prevCount = elements.count()

        while (true) {
            elements = elements.flatMap {
                if (it.isLeaf()) it.iterator() else buildChildren(it)
            }

            val count = elements.count()
            if (count == prevCount) {
                break
            }
            prevCount = count
        }
    }

    private fun <T> T.iterator(): Iterator<T> {
        return listOf(this).iterator()
    }
}

data class Node(val state: State,
                private val row: Int,
                private val column: Int) : Serializable {

    var parent: Node? = null
    private val children = mutableListOf<Node>()

    constructor(state: State, row: Int, column: Int, parent: Node) : this(state, row, column) {
        this.parent = parent
    }

    fun addChildren(children: List<Node>) {
        this.children.addAll(children)
    }

    fun isLeaf(): Boolean {
        return false
    }
}


enum class State {
    Untouched, Cross, Circle
}