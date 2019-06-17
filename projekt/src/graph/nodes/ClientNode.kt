package graph.nodes

import graph.Stats
import graph.signals.Signal
import kotlin.random.Random

class ClientNode(id: String, private val maxTimeout: Int, private val stats: Stats, private val probability: Double) : NodeInterface {
    private lateinit var serverNode: NodeInterface
    private val signals = mutableListOf<Signal>()
    private var timeout = nextEmitting()

    private var emitting = false

    override fun end(): NodeInterface {
        if (!::serverNode.isInitialized) return this@ClientNode
        return serverNode.end()
    }

    override fun addTo(node: NodeInterface) {
        serverNode = node
    }

    override fun step() {
        if (emitting) {
            serverNode.signal(Signal(this, this))
            if (timeout == 0) {
                emitting = false
                timeout = nextEmitting()

                stats.succeses++
            }
        } else {
            if (timeout == 0) {
                if (signals.filter { it.of != this }.isNotEmpty()) {
                    timeout = nextEmitting()

                    stats.waits++
                } else {
                    emitting = true
                    timeout = 2*maxTimeout + 2

                    stats.tries++
                }
            }
        }
        signals.clear()
        timeout--
    }

    override fun signal(signal: Signal) {
        signals.add(signal)
        if (emitting && signal.of != this) {
            emitting = false
            timeout = nextEmitting()

            stats.collisions++
        }
    }

    private fun nextEmitting(): Int {
        var i = 1
        while (Random.nextDouble() > probability) i++
        return i
    }
}