package graph.nodes

import graph.Stats
import graph.signals.Signal
import kotlin.random.Random

class ClientNode(id: String, private val maxTimeout: Int, private val stats: Stats, private val probability: Double) : NodeInterface {
    private lateinit var serverNode: ServerNode
    private var timeout = nextEmitting()

    private var emitting = false

    override fun addTo(node: NodeInterface) {
        if (node !is ServerNode) throw Exception("Only server node can be added to ClientNodes")
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
                emitting = true
                timeout = nextEmitting()

                stats.tries++
            }
        }
        timeout--
    }

    override fun signal(signal: Signal) {
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