package graph

import graph.nodes.EmitterNode
import graph.nodes.NodeInterface
import graph.nodes.RelayNode
import java.lang.Math.abs
import kotlin.random.Random

class CircularGraph(private val size: Int, private val emitters: Int) {
    private val nodes = arrayOfNulls<NodeInterface>(size)

    init {
        for (i in 0 until emitters) {
            val idx = abs(Random.nextInt()) % size
            nodes[idx] = EmitterNode("T:$idx", size)
        }
        for (i in 0 until nodes.size) {
            if (nodes[i] == null) nodes[i] = RelayNode("R:$i")
        }
        for (i in 0 until nodes.size - 1) {
            if (i == 0) {
                nodes[0]?.to(nodes[1])
                nodes[0]?.to(nodes[nodes.size - 1])

                nodes[nodes.size - 1]?.to(nodes[0])
                nodes[nodes.size - 1]?.to(nodes[nodes.size - 2])
            } else {
                println("${nodes[i]} adds ${nodes[i-1]} and ${nodes[i+1]}")
                nodes[i]?.to(nodes[i-1])
                nodes[i]?.to(nodes[i+1])
            }
        }
    }

    public fun step() {
        for (node in nodes) node!!.step()
    }
}