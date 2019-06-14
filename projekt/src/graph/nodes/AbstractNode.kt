package graph.nodes

import graph.signals.Signal

abstract class AbstractNode(private val id: String) : NodeInterface {
    private val toList = mutableListOf<NodeInterface>()
    public val signals = mutableListOf<Signal>()
    private val incomingSignals = mutableListOf<Signal>()

    override fun addTo(node: NodeInterface) {
        toList.add(node)
    }

    override fun step() {
        //for every signal
        for (signal in signals) {
            //for every receiving node
            for (node in toList) {
                //transmit it everywhere
                //println("$this -> $node : $signal")
                if (signal.prev != node) node.signal(Signal(signal.of, this))
            }
        }
        signals.clear()
        signals.addAll(incomingSignals)
        incomingSignals.clear()
    }

    override fun signal(signal: Signal) {
        incomingSignals.add(signal)
    }

    override fun toString(): String {
        return "n#$id"
    }
}