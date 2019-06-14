package graph.signals

import graph.nodes.NodeInterface

data class Signal(val of: NodeInterface?, val prev: NodeInterface?) {
    override fun toString(): String {
        return "sig@$of"
    }
}