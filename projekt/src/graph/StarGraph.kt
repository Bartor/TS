package graph

import graph.nodes.ClientNode
import graph.nodes.NodeInterface
import graph.nodes.RelayNode
import graph.nodes.ServerNode

class StarGraph(size: Int, clients: Int, probability: Double) : AbstractGraph() {
    private val nodes = mutableListOf<NodeInterface>()
    private val relays = mutableListOf<NodeInterface>()
    private val server = ServerNode("server")

    public override val stats = Stats()

    init {
        //create clients
        for (i in 0 until clients) {
            val client = ClientNode("c#$i", size, stats, probability)
            nodes.add(client)
        }
        //create randomized connections
        for (i in 0 until size) {
            val client = nodes.random()
            val end = client.end()
            val relay = RelayNode("r#$i")

            client.end().addTo(client.end())
            end.addTo(relay)
            relays.add(relay)
        }
        //connect to the server
        for (node in nodes) {
            server.addTo(node.end())
            node.end().addTo(server)
        }
    }

    public override fun step() {
        for (node in nodes) node.step()
        for (relay in relays) relay.step()
        server.step()
    }
}