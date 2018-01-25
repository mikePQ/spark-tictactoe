package pl.edu.agh.eaiib.io.ztb.ui

import javafx.concurrent.Task
import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import pl.edu.agh.eaiib.io.ztb.Results
import tornadofx.*

class TicTacToeView : View("Kółko i krzyżyk - kalkulator prawdopodobieństwa") {
    private val controller = TicTacToeController()

    private lateinit var currentPlayerLabel: Label
    private val pane = createBoard(anchorpane {
        minHeight = 600.0
        maxHeight = 600.0
        minWidth = 1000.0
        maxWidth = 1000.0

        menubar {
            minWidth = 1000.0
            maxWidth = 1000.0

            menu("Plansza") {
                val toggleGroup = ToggleGroup()

                radiomenuitem("3x3", toggleGroup) {
                    isSelected = controller.boardSize == BoardSize.Normal

                    action {
                        controller.changeBoardSize(BoardSize.Normal)
                        rebuildBoardPane()
                    }
                }

                radiomenuitem("4x4", toggleGroup) {
                    isSelected = controller.boardSize == BoardSize.Enlarged

                    action {
                        controller.changeBoardSize(BoardSize.Enlarged)
                        rebuildBoardPane()
                    }
                }
            }
        }

        button("Wyczyść planszę") {
            layoutX = 665.0
            layoutY = 450.0

            action {
                controller.clearBoard()
                rebuildBoardPane()
                updateCurrentPlayerLabel()
            }
        }

        button("Oblicz prawdopodobieństwo wygranej") {
            layoutX = 665.0
            layoutY = 515.0

            action {
                val task: Task<Results> = runAsyncWithProgress {
                    controller.calculateResults()
                }

                task.onSucceeded = EventHandler {
                    information("Wyniki obliczeń", task.get().prettyPrint(), title="Wyniki obliczeń")
                }
            }
        }

        label("Następny gracz:") {
            layoutX = 665.0
            layoutY = 70.0
        }

        currentPlayerLabel = label("x") {
            layoutX = 790.0
            layoutY = 60.0

            addClass(Styles.boardText)
            font = Font.font("Verdana", FontWeight.EXTRA_LIGHT, 25.0)
        }

    })

    override val root = pane

    private fun createBoard(pane: Pane): Pane {
        val boardWidth = 600.0
        val boardHeight = 500.0

        pane.gridpane {
            isGridLinesVisible = true

            hgap = 1.0
            vgap = 1.0

            layoutX = 40.0
            layoutY = 70.0

            minHeight = boardHeight
            maxHeight = boardHeight

            minWidth = boardWidth
            maxWidth = boardWidth

            val maxIndex = controller.boardSize.rows - 1
            val cellWidth = boardWidth / (maxIndex + 1)
            val cellHeight = boardHeight / (maxIndex + 1)
            (0..maxIndex).forEach {
                val rowIdx = it
                row {
                    (0..maxIndex).forEach {
                        val columnIdx = it
                        stackpane {
                            rectangle {
                                height = cellHeight
                                width = cellWidth

                                addClass(Styles.boardCell)
                            }

                            val text = controller.getText(rowIdx, columnIdx)
                            val label = label(text) {
                                addClass(Styles.boardText)
                                font = Font.font("Verdana", FontWeight.EXTRA_LIGHT, 90.0)
                            }

                            setOnMouseClicked {
                                controller.handleClick(rowIdx, columnIdx)
                                label.text = controller.getText(rowIdx, columnIdx)
                                updateCurrentPlayerLabel()
                            }
                        }
                    }
                }
            }

            addClass(Styles.board)
        }

        return pane
    }

    private fun rebuildBoardPane() {
        val children = pane.children

        val board = children.firstOrNull { it is GridPane }
        if (board != null) {
            children.remove(board)
            createBoard(pane)
        }
    }

    private fun updateCurrentPlayerLabel() {
        currentPlayerLabel.text = if (controller.currentPlayerIsCross) "x" else "o"
    }
}
