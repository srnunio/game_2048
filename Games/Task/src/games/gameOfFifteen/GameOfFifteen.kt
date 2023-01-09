package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import games.game2048.addNewValue

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {

    private val board: GameBoard<Int?> = createGameBoard(4)

    override fun initialize() = board.addValues(initializer)

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        var k = 1
        var won = true
        for (i in 1..board.width) {
            for (j in 1..board.width) {
                val value = board[board.getCell(i, j)]
                if (k <= 15) {
                    if (value != k) {
                        won = false
                        break;
                    }
                    k++
                }
            }
        }
        return won && get(board.width, board.width) == null
    }

    override fun processMove(direction: Direction) = board.moveValuesFifteen(direction)

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}

private fun GameBoard<Int?>.addValues(initializer: GameOfFifteenInitializer) {
    val data = initializer.initialPermutation
    var k = 0
    for (i in 1..width) {
        for (j in 1..width) {
            var value: Int?
            if (k < 15) {
                value = data[k]
                k++
            } else {
                value = null
            }

            this[this.getCell(i, j)] = value

        }
    }

}

private fun GameBoard<Int?>.swapValues(rowToBeUpdated: Cell, emptyCells: List<Cell>) {
    val temp = this[rowToBeUpdated]
    this[rowToBeUpdated] = this[emptyCells.get(0)]
    this[emptyCells.get(0)] = temp
}

fun GameBoard<Int?>.moveValuesFifteen(direction: Direction) {

    val emptyCells = this.getAllCells().filter { it -> this[it] == null }

    when (direction) {
        Direction.LEFT -> {

            val rowToBeUpdated = this.getCell(emptyCells.get(0).i, emptyCells.get(0).j + 1)
            swapValues(rowToBeUpdated, emptyCells)
        }

        Direction.RIGHT -> {
            val rowToBeUpdated = this.getCell(emptyCells.get(0).i, emptyCells.get(0).j - 1)
            swapValues(rowToBeUpdated, emptyCells)
        }

        Direction.DOWN -> {
            val rowToBeUpdated = this.getCell(emptyCells.get(0).i - 1, emptyCells.get(0).j)
            swapValues(rowToBeUpdated, emptyCells)
        }

        Direction.UP -> {
            val rowToBeUpdated = this.getCell(emptyCells.get(0).i + 1, emptyCells.get(0).j)
            swapValues(rowToBeUpdated, emptyCells)
        }
    }
}