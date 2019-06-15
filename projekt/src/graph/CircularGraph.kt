package graph

import graph.nodes.EmitterNode
import graph.nodes.NodeInterface
import graph.nodes.RelayNode
import java.lang.Math.abs
import kotlin.random.Random

class CircularGraph(size: Int, emitters: Int, probability: Double, nodeChance: Double) : AbstractGraph() {
    private val nodes = arrayOfNulls<NodeInterface>(size)
    public override val stats = Stats()

    init {
        for (i in 0 until emitters) {
            val idx = abs(Random.nextInt()) % size
            nodes[idx] = EmitterNode("T:$idx", size, stats, probability)
        }
        for (i in 0 until nodes.size) {
            if (nodes[i] == null) nodes[i] = RelayNode("R:$i")
        }
        for (i in 0 until nodes.size - 1) {
            if (i == 0) {
                if (Random.nextDouble() < nodeChance) {
                    nodes[0]?.addTo(nodes[1]!!)
                    nodes[0]?.addTo(nodes[nodes.size - 1]!!)

                    nodes[nodes.size - 1]?.addTo(nodes[0]!!)
                    nodes[nodes.size - 1]?.addTo(nodes[nodes.size - 2]!!)
                }
            } else {
                nodes[i]?.addTo(nodes[i-1]!!)
                nodes[i]?.addTo(nodes[i+1]!!)
            }
        }
    }

    public override fun step() {
        for (node in nodes) node!!.step()
    }
}