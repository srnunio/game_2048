package games.gameOfFifteen

import kotlin.random.Random

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val random = java.util.Random()
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)

        val shuffledArray =  list.shuffled(random).toMutableList()
        var index = 0

        while (!isEven(shuffledArray)){
            val temp = shuffledArray[index]
            shuffledArray[index] = shuffledArray[index + 1]
            shuffledArray[index + 1] = temp
            index = index + 1
            if(index == list.size) index = 1
        }
        shuffledArray.toList()

    }
}

