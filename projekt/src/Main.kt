import com.google.gson.Gson
import graph.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*
import java.util.concurrent.CountDownLatch

const val TRIES = 100 //how many simulations are held

fun main(args: Array<String>) {
    val finalStats = arrayOfNulls<Array<Array<StatyStats?>?>?>(10)
    for (size in 10..100 step 10) {
        finalStats[size/10 - 1] = arrayOfNulls(10)
        for (emitters in 1..10) {
            finalStats[size/10 - 1]!![emitters - 1] = arrayOfNulls(10)
            for (probability in 1..10) {
                print("SIZE: $size EMITTERS: $emitters PROBABILITY: ${probability/100.0}")

                val latch = CountDownLatch(4)

                var circularFullStats = Stats()
                Thread {
                    circularFullStats = launchThreaded(TRIES) {
                        getStats(CircularGraph(size, emitters, probability/100.0, 1.0), size*size)
                    }
                    print(".")
                }.start()

                var circularHalfStats = Stats()
                Thread {
                    circularHalfStats = launchThreaded(TRIES) {
                        getStats(CircularGraph(size, emitters, probability/100.0, 0.5), size*size)
                    }
                    latch.countDown()
                    print(".")
                }.start()

                var circularQuarterStats = Stats()
                Thread {
                    circularQuarterStats = launchThreaded(TRIES) {
                        getStats(CircularGraph(size, emitters, probability/100.0, 0.25), size*size)
                    }
                    latch.countDown()
                    print(".")
                }.start()

                var starStats = Stats()
                Thread {
                    starStats = launchThreaded(TRIES) {
                        getStats(StarGraph(size, emitters, probability/100.0), emitters*emitters)
                    }
                    latch.countDown()
                    print(".")
                }.start()

                latch.await()

                finalStats[size/10 - 1]!![emitters - 1]!![probability - 1] = StatyStats(circularFullStats, circularHalfStats, circularQuarterStats, starStats)
                println("\tdone")
            }
        }
    }

    File("$TRIES-${Calendar.getInstance().timeInMillis}.json").writeText(Gson().toJson(finalStats))
}

fun getStats(g: AbstractGraph, steps: Int): Stats {
    for (j in 0..steps) {
        g.step()
    }
    return g.stats
}

fun launchThreaded(tries: Int, what: () -> Stats) : Stats {
    val localStats = mutableListOf<Stats>()
    runBlocking {
        for (i in 0 until tries) {
            launch {
                val s = what()
                synchronized(localStats) {
                    localStats.add(s)
                }
            }
        }
    }
    val res = Stats()
    for (s in localStats) {
        res.collisions += s.collisions/TRIES
        res.succeses += s.succeses/TRIES
        res.tries += s.tries/TRIES
        res.waits += s.waits/TRIES
    }
    return res
}