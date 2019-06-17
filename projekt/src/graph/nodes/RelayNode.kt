package graph.nodes

class RelayNode(id: String) : AbstractNode(id) {
    override fun end(): NodeInterface {
        if (toList.isEmpty()) return this@RelayNode
        return toList.last().end()
    }
}