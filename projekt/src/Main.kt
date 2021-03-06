import com.google.gson.Gson
import graph.*
import kotlinx.coroutines.*
import java.io.File
import java.util.*

const val TRIES = 100 //how many simulations are held

fun main(args: Array<String>) = runBlocking {
    val jobs = mutableListOf<Job>()
    val finalStats = arrayOfNulls<Array<Array<StatyStats?>?>?>(10)
    for (size in 10..100 step 10) {
        finalStats[size / 10 - 1] = arrayOfNulls(10)
        for (emitters in 1..10) {
            finalStats[size / 10 - 1]!![emitters - 1] = arrayOfNulls(10)
            for (probability in 1..10) {
                jobs.add(launch(Dispatchers.Default) {
                    print("SIZE: $size EMITTERS: $emitters PROBABILITY: ${probability / 100.0}")

                    val circularFullStats = Stats()
                    for (i in 0..TRIES) {
                        val s = getStats(CircularGraph(size, emitters, probability / 100.0, 1.0), size * size)
                        circularFullStats.collisions += s.collisions / TRIES
                        circularFullStats.succeses += s.succeses / TRIES
                        circularFullStats.tries += s.tries / TRIES
                        circularFullStats.waits += s.waits / TRIES
                    }
                    print(".")


                    val circularHalfStats = Stats()
                    for (i in 0..TRIES) {
                        val s = getStats(CircularGraph(size, emitters, probability / 100.0, 0.5), size * size)
                        circularHalfStats.collisions += s.collisions / TRIES
                        circularHalfStats.succeses += s.succeses / TRIES
                        circularHalfStats.tries += s.tries / TRIES
                        circularHalfStats.waits += s.waits / TRIES
                    }
                    print(".")

                    val circularQuarterStats = Stats()
                    for (i in 0..TRIES) {
                        val s = getStats(CircularGraph(size, emitters, probability / 100.0, 0.25), size * size)
                        circularQuarterStats.collisions += s.collisions / TRIES
                        circularQuarterStats.succeses += s.succeses / TRIES
                        circularQuarterStats.tries += s.tries / TRIES
                        circularQuarterStats.waits += s.waits / TRIES
                    }
                    print(".")

                    val starStats = Stats()
                    for (i in 0..TRIES) {
                        val s = getStats(StarGraph(size, emitters, probability / 100.0), emitters * emitters)
                        starStats.collisions += s.collisions / TRIES
                        starStats.succeses += s.succeses / TRIES
                        starStats.tries += s.tries / TRIES
                        starStats.waits += s.waits / TRIES
                    }
                    print(".")


                    synchronized(finalStats) {
                        finalStats[size / 10 - 1]!![emitters - 1]!![probability - 1] =
                                StatyStats(circularFullStats, circularHalfStats, circularQuarterStats, starStats)
                    }
                    println("\tdone")
                })
            }
        }
    }

    for (job in jobs) job.join()

    File("$TRIES-${Calendar.getInstance().timeInMillis}.json").writeText(Gson().toJson(finalStats))
}

fun getStats(g: AbstractGraph, steps: Int): Stats {
    for (j in 0..steps) {
        g.step()
    }
    return g.stats
}