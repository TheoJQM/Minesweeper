fun main() {
    var (num1, op, num2) = readln().split(" ")

    val val1 = num1.toLong()
    val val2 = num2.toLong()

println(when {
        op == "+" -> val1 + val2
        op == "-" -> val1 - val2
        op == "*" -> val1 * val2
        op == "/" -> if(val2 == 0L) "Division by 0!" else val1 / val2
        else -> "Unknown operator"
    })
}
