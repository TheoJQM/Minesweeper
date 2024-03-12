fun main() {    
    val shape = readln()
    when(shape) {
        "triangle" -> calculateTriangleArea(readln().toDouble(), readln().toDouble(), readln().toDouble())
        "rectangle" -> calculateRectangleArea(readln().toDouble(), readln().toDouble())
        "circle" -> calculateCircleArea(readln().toDouble())
    }
}

fun calculateTriangleArea(a: Double, b: Double, c:Double) {
    val s = (a+b+c)/2
    val area = Math.sqrt(s*(s-a)*(s-b)*(s-c))
    println(area)
}

fun calculateRectangleArea(x: Double, y: Double) {
    println(x * y)
}

fun calculateCircleArea(r: Double) {
    println(3.14 * r*r)
}
