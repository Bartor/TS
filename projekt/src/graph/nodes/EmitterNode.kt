package graph.nodes

import graph.signals.Signal
import kotlin.random.Random

class EmitterNode(private val maxTimeout: Int) : AbstractNode() {
    private var timeout = Random.nextInt() % maxTimeout

    override fun step() {
        if (timeout-- == 0) {
            if (signals.isEmpty()) {
                signals.add(Signal(this, null))
            } else {
                timeout = Random.nextInt() % maxTimeout
            }
        }
    }
}