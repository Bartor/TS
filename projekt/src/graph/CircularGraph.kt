package graph

import graph.nodes.EmitterNode
import graph.nodes.NodeInterface
import graph.nodes.RelayNode
import java.lang.Math.abs
import kotlin.random.Random

class CircularGraph(private val size: Int, private val emitters: Int) {
    public val nodes = arrayOfNulls<NodeInterface>(size)

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
                println("${nodes[0]} adds ${nodes[1]} and ${nodes[nodes.size - 1]}")
                nodes[0]?.addTo(nodes[1]!!)
                nodes[0]?.addTo(nodes[nodes.size - 1]!!)

                println("${nodes[nodes.size -1 ]} adds ${nodes[0]} and ${nodes[nodes.size - 2]}")
                nodes[nodes.size - 1]?.addTo(nodes[0]!!)
                nodes[nodes.size - 1]?.addTo(nodes[nodes.size - 2]!!)
            } else {
                println("${nodes[i]} adds ${nodes[i-1]} and ${nodes[i+1]}")
                nodes[i]?.addTo(nodes[i-1]!!)
                nodes[i]?.addTo(nodes[i+1]!!)
            }
        }
    }

    public fun step() {
        for (node in nodes) node!!.step()
    }
}