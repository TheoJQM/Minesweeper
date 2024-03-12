fun main() {
    val nbNumbers = readln().toInt()
    val list = mutableListOf<Int>()
    for( num in 1..nbNumbers){
        list.add(readln().toInt())
    }

    var nbRotate = readln().toInt() % list.size

    while(nbRotate > 0) {
        val temp = list.last()
        list.removeAt(list.lastIndex)
        list.add(0, temp)
        nbRotate--
    }
    
    println(list.joinToString(" "))
}
