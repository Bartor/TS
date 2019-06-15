import graph.CircularGraph
import graph.Stats

const val TRIES = 100 //how many simulations are held

const val SIZE = 100 //size of the graph
const val EMITTERS = 5 //number of emitting nodes
const val PROB = 0.05 //probability of emission in an arbitrary step
const val CHANCE = 0.5 //probability of not connecting every node in circular graph

fun main(args: Array<String>) {
    val stats = mutableListOf<Stats>(); for (i in 0..TRIES) {
        val g = CircularGraph(SIZE, EMITTERS, PROB, CHANCE)
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