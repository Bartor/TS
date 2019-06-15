import graph.CircularGraph
import graph.Stats

const val TRIES = 100
const val SIZE = 100
const val EMITTERS = 5
const val PROB = 0.05

fun main(args: Array<String>) {
    val stats = mutableListOf<Stats>(); for (i in 0..TRIES) {
        val g = CircularGraph(SIZE, EMITTERS, PROB)
        for (j in 1..SIZE*SIZE) {
            g.step()
        }
        stats.add(g.stats)
    }
    val avg = Stats()
    for (stat in stats) {
        avg.collisions += stat.collisions/stats.size
        avg.succeses += stat.succeses/stats.size
        avg.tries += stat.tries/stats.size
        avg.waits += stat.waits/stats.size
    }
    println(avg)
}