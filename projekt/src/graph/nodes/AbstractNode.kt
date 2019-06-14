package graph.nodes

import graph.signals.Signal

abstract class AbstractNode : NodeInterface {
    private val toList = mutableListOf<NodeInterface>()
    protected val signals = mutableListOf<Signal>()

    override fun from(node: NodeInterface) {
        toList.add(node)
    }

    override fun to(node: NodeInterface) {
        node.from(this)
    }

    override fun step() {
        for (node in toList) {
            for (signal in signals) {
                if (signal.prev != node) node.signal(Signal(signal.of, this))
            }
        }
    }

    override fun signal(signal: Signal) {
        signals.add(signal)
    }
}