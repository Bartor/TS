package graph.nodes

import graph.signals.Signal

interface NodeInterface {
    fun addTo(node: NodeInterface)
    fun signal(signal: Signal)
    fun step()
}