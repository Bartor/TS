import graph.CircularGraph
import graph.Stats

const val TRIES = 100

fun main(args: Array<String>) {
    val stats = mutableListOf<Stats>()
    for (i in 0..TRIES) {
        val g = CircularGraph(1000, 5)
        for (j in 1..TRIES*TRIES) {
            g.step()
        }
        stats.add(g.stats)
    }
    println(stats)
}