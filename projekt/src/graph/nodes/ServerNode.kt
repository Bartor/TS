package graph.nodes

import graph.signals.Signal

class ServerNode(id: String) : AbstractNode(id) {
    private var emitter: NodeInterface? = null

    override fun end(): NodeInterface {
        return this@ServerNode
    }

    override fun step() {
        if (incomingSignals.isEmpty()) {
            emitter = null
        }
        super.step()
    }

    override fun signal(signal: Signal) {
        if (emitter == null) {
            incomingSignals.add(signal)
            emitter = signal.of
        }
    }
}