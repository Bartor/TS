package graph.plotters

import graph.CircularGraph
import graph.nodes.AbstractNode
import graph.nodes.EmitterNode
import graph.nodes.RelayNode

class CircGraphPlotter {
    companion object {
        fun plot(g: CircularGraph): String {
            val builder = StringBuilder()
            val lengths = mutableListOf<Int>()
            for (n in g.nodes) {
                val l = builder.length
                builder.append(n.toString())
                lengths.add(builder.length - l)
                builder.append("|")
            }
            builder.append("\n")
            for ((i, n) in g.nodes.withIndex()) {
                builder.append(
                    when (n) {
                        is EmitterNode -> {
                            if (n.emitting) {
                                if (n.collision) {
                                    "C"
                                } else {
                                    "E"
                                }
                            } else {
                                if (n.collision) {
                                    "?"
                                } else {
                                    "W"
                                }
                            }
                        }
                        is RelayNode -> {
                            "_"
                        }
                        else -> "?"
                    }.padEnd(lengths[i])
                )
                builder.append("|")
            }
            builder.append("\n")
            var flag = false
            var level = 0
            while (!flag) {
                flag = false
                for ((i, n) in g.nodes.withIndex()) {
                    if ((n as AbstractNode).signals.size > level) {
                        builder.append(n.signals[level].toString().take(lengths[i]).padEnd(lengths[i]))
                        flag = true
                    } else {
                        builder.append("".padEnd(lengths[i]))
                    }
                    builder.append("|")
                }
                if (flag) level++
                builder.append("\n")
            }
            return builder.toString()
        }
    }
}