package graph.nodes

import graph.signals.Signal

abstract class AbstractNode(private val id: String) : NodeInterface {
    private val toList = mutableListOf<NodeInterface>()
    protected val signals = mutableListOf<Signal>()

    override fun to(node: NodeInterface) {
        toList.add(node)
    }

    override fun step() {
        //for every signal
        for (signal in signals) {
            //for every receiving node
            for (node in toList) {
                //transmit it everywhere
                println("$this sends $signal to $node")
                if (signal.prev != node) node.signal(Signal(signal.of, this))
            }
            //and remove
            signals.remove(signal)
        }
    }

    override fun signal(signal: Signal) {
        signals.add(signal)
    }

    override fun toString(): String {
        return "Node#$id"
    }
}