import graph.CircularGraph

fun main(args: Array<String>) {
    val g = CircularGraph(10, 2)
    for (i in 1..100) {
        println("---STEP $i---")
        g.step()
    }
}