import graph.CircularGraph
import graph.plotters.CircGraphPlotter

fun main(args: Array<String>) {
    val g = CircularGraph(10, 5)
    for (i in 1..100) {
        println("\u001b[H\u001b[2J")
        g.step()
        println(CircGraphPlotter.plot(g))
        Thread.sleep(500)
    }
}