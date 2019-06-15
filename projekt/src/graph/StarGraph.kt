package graph

import graph.nodes.ClientNode
import graph.nodes.NodeInterface
import graph.nodes.ServerNode

class StarGraph(private val size: Int, private val probability: Double) : AbstractGraph() {
    private val nodes = mutableListOf<NodeInterface>()
    private val server = ServerNode("server")

    public override val stats = Stats()

    init {
        for (i in 0 until size) {
            val client = ClientNode("c#$i", size, stats, probability)
            client.addTo(server)
            server.addTo(client)
            nodes.add(client)
        }
    }

    public override fun step() {
        for (node in nodes) node.step()
        server.step()
    }
}