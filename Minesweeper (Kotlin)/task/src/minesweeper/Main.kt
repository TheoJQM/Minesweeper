package minesweeper
import kotlin.random.*

const val FIELDS = 9

class Minesweeper{
    private val field = MutableList(FIELDS) {MutableList(FIELDS) {Pair(false,".") } }
    private val minePositions = mutableListOf<IntArray>()
    private val emptyCellsPositions = mutableListOf<IntArray>()
    private val playerMarks = mutableListOf<IntArray>()
    private val exploredCells = mutableListOf<IntArray>()
    private var mineTouched = false

    fun play() {
        print("How many mines do you want on the field?")
        prepareField(readln().toInt())

        while (!isGameOver()) {
            print("Set/delete mines marks or claim a cell as free:")
            val pos =  readln().split(" ")
            val position = Triple(pos[0].toInt(), pos[1].toInt(), pos[2])
            if (checkInput(position.first, position.second, position.third))
                processInputs(position.first, position.second, position.third)
            displayField()
        }
    }

    private fun prepareField(nbMines : Int) {
        createMines(nbMines)

        for (i in 0 until FIELDS) {
            for (j in 0 until FIELDS) {
                if (!minePositions.any{ it.contentEquals(intArrayOf(i,j))}) setNearMines(i, j)
            }
        }

        getEmptyCells()

        displayField()
    }

    private fun createMines(nbMines : Int) {
        var count = 0
        while (count < nbMines) {
            val x = Random.nextInt(FIELDS)
            val y = Random.nextInt(FIELDS)
            if (!minePositions.contains(intArrayOf(x, y))) {
                minePositions.add(intArrayOf(x, y))
                count++
            }
        }
    }

    private fun setNearMines(x: Int, y: Int) {
        var nbMines = 0
        for (i in -1..1) {
            for (j in -1..1) {
                try {
                    if (minePositions.any{it.contentEquals(intArrayOf(x + i, y + j))}) nbMines++
                } catch (_: Exception) {
                }
            }
        }
        field[x][y] = field[x][y].copy(second = nbMines.toString())
    }

    private fun getNearMines(x: Int, y: Int): Int {
        var nbMines = 0
        for (i in -1..1) {
            for (j in -1..1) {
                try {
                    if (minePositions.any{it.contentEquals(intArrayOf(x + i, y + j))}) nbMines++
                } catch (_: Exception) {
                }
            }
        }
        return nbMines
    }

    private fun getEmptyCells() {
        for (i in 0 until FIELDS) {
            for (j in 0 until FIELDS) {
                if (field[i][j].second != ".") emptyCellsPositions.add(intArrayOf(i,j))
            }
        }
    }

    private fun checkInput(x: Int, y: Int, action: String): Boolean {
        return when {
            action != "mine" && action != "free" -> false
            x !in 0..9 || y !in 0..9 -> false
            else -> true
        }
    }

    private fun processInputs(x: Int, y: Int, action: String) {
        if (action == "mine") markCell(y-1,x-1) else exploreCell(y-1, x-1)
    }

    private fun markCell(x: Int, y: Int) {
        when (field[x][y].second) {

            "*" -> {
                val value = getNearMines(x, y)
                field[x][y] = field[x][y].copy(second = if (minePositions.any { it.contentEquals(intArrayOf(x, y))}) "." else value.toString())
                playerMarks.remove(intArrayOf(x, y))
            }
            else -> {
                field[x][y] = field[x][y].copy(second = "*")
                playerMarks.add(intArrayOf(x, y))
            }
        }
    }

    private fun exploreCell(x: Int, y: Int) {
        when {
            field[x][y].first -> {}

            field[x][y].second == "*" -> {
                val value = getNearMines(x, y)
                field[x][y] = field[x][y].copy(true, if (value == 0) "/" else value.toString())
                if (value == 0) {
                    for (i in -1..1) {
                        for (j in -1..1) {
                            try {
                                if (exploredCells.any { !it.contentEquals(intArrayOf(x + i, y + j)) } ) exploreCell(x + i, y + j)
                            } catch (_: Exception) {
                            }
                        }
                    }
                }
            }

             minePositions.any { it.contentEquals(intArrayOf(x, y)) } -> {
                for (mine in minePositions) {
                    field[mine.first()][mine.last()] = field[mine.first()][mine.last()].copy(true, "X")
                }
                mineTouched = true
            }

            field[x][y].second.toInt() in 1..8 -> {
                field[x][y] = field[x][y].copy(true)
                exploredCells.add(intArrayOf(x, y))
            }


            else -> {
                exploredCells.add(intArrayOf(x, y))
                field[x][y] = field[x][y].copy(true, "/")
                for (i in -1..1) {
                    for (j in -1..1) {
                        try {
                            if (exploredCells.any { !it.contentEquals(intArrayOf(x + i, y + j)) } ) exploreCell(x + i, y + j)
                        } catch (_: Exception) {
                        }
                    }
                }
            }
        }
    }

    private fun isGameOver(): Boolean {
        return if (mineTouched) {
            println("You stepped on a mine and failed!")
             true
        }else if (areListsEquals()) {
            println("Congratulations! You found all the mines!")
            true
        } else {
            false
        }
    }

    private fun areListsEquals(): Boolean {
        // Check the mines
        val mineList = minePositions.map { it.sorted() }.sortedBy { it.toString() }
        val playerList = playerMarks.map { it.sorted() }.sortedBy { it.toString() }

        // Check the empty cells
        val emptyCellsList = emptyCellsPositions.map { it.sorted() }.sortedBy { it.toString() }
        val exploredCellsList = exploredCells.map { it.sorted() }.sortedBy { it.toString() }

        return mineList == playerList || emptyCellsList == exploredCellsList
    }

    private fun displayField() {
        println("\n |123456789|")
        println("—|—————————|")
        for (i in 0 until field.size) {
            println("${i + 1}|${field[i].joinToString("") { t -> if (t.first || t.second == "*") t.second else "." }}|")
        }
        println("—|—————————|")
    }
}

fun main() {
    val game = Minesweeper()
    game.play()
}