package board

import board.Direction.*

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    var cells: Array<Array<Cell>> = arrayOf(arrayOf())

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i > width || j > width || i == 0 || j == 0) return null
        return getCell(i, j)
    }

    override fun getCell(i: Int, j: Int): Cell = cells[i - 1][j - 1]

    override fun getAllCells(): Collection<Cell> {
        return IntRange(1, width).flatMap { i ->
            IntRange(1, width).map { j -> getCell(i, j) }
        }.toList()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if (jRange.last > width)
            return IntRange(jRange.first, width).map { j -> getCell(i, j) }.toList()
        return jRange.map { j -> getCell(i, j) }.toList()
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if (iRange.last > width)
            return IntRange(iRange.first, width).map { i -> getCell(i, j) }.toList()
        return iRange.map { i -> getCell(i, j) }.toList()
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? = when (direction) {
        UP -> getCellOrNull(i - 1, j)
        LEFT -> getCellOrNull(i, j - 1)
        DOWN -> getCellOrNull(i + 1, j)
        RIGHT -> getCellOrNull(i, j + 1)
    }


}

class GameBoardImpl<T>(val with: Int) : SquareBoardImpl(with), GameBoard<T> {

    val cellValues = mutableMapOf<Cell, T?>()

    override fun get(cell: Cell): T? {
        return cellValues.get(cell)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellValues.values.all(predicate)
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellValues.values.any(predicate)
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return cellValues.filter { predicate.invoke(it.value) }.keys.first()
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return cellValues.filterValues { predicate.invoke(it) }.keys
    }

    override fun set(cell: Cell, value: T?) {
        cellValues += cell to value
    }

}

private fun createEmptyBoard(width: Int): Array<Array<Cell>> {
    var list = arrayOf<Array<Cell>>()

    for (i in 1..width) {
        var internalList = arrayOf<Cell>()
        for (j in 1..width) {
            internalList += Cell(i, j)
        }
        list += internalList
    }
    return list
}

fun createSquareBoard(width: Int): SquareBoard {
    val squareBoard = SquareBoardImpl(width)
    squareBoard.cells = createEmptyBoard(width)

    return squareBoard

}

fun <T> createGameBoard(width: Int): GameBoard<T> {

    val gameBoard = GameBoardImpl<T>(width)

    gameBoard.cells = createEmptyBoard(width)
    gameBoard.cells.forEach { it.forEach { cell -> gameBoard.cellValues += cell to null } }

    return gameBoard
}

