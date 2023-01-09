package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    var inversions = 0

    for (counter in permutation.indices) {
        for (index in counter + 1 until permutation.size) {
            if (isInverse(permutation, counter, index)) inversions++
        }
    }

    return inversions % 2 == 0
}

private fun isInverse(permutation: List<Int>, x: Int, y: Int): Boolean {
    return x < y && permutation[x] > permutation[y]
}
