fun main() {
    val fib = intArrayOf(0, 1, 2, 3, 5, 8, 13, 21, 34, 55)
    val tri = intArrayOf(0, 1, 3, 6, 10, 15, 21, 28, 36, 45)
    val ten = intArrayOf(1, 10, 100, 1000, 10000, 100000)

    val input = readln().toInt()
    when  {
        input in fib -> println("F")
        input in tri -> println("T")
        input in ten -> println("P")
        else -> println("N")
    }
}
