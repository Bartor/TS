package graph.nodes

import graph.signals.Signal

interface NodeInterface {
    fun to(node: NodeInterface)
    fun signal(signal: Signal)
    fun step()
}