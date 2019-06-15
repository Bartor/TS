package graph.nodes

import graph.Stats
import graph.signals.Signal
import java.lang.Math.abs
import kotlin.math.sign
import kotlin.random.Random

class EmitterNode(id: String, private val maxTimeout: Int, private val stats: Stats, private val probability: Double) : AbstractNode(id) {
    private var collisions = 0
    private var timeout = nextEmitting()

    public var emitting = false
        private set
    public var collision = false
        private set

    override fun step() {
        for (signal in signals) {
            for (node in toList) {
                if (signal.prev != node) node.signal(Signal(signal.of, this))
            }
        }
        if (emitting) {
            //if we're emitting, we always signal
            incomingSignals.add(Signal(this, this))
            if (collision) {
                //emitting with collision
                if (timeout == 0) {
                    //we stopped emitting with collision
                    emitting = false
                    collision = false
                    timeout = maxTimeout * (0b1 shl (Random.nextInt() % collisions))
                }
            } else {
                //emitting and no collision
                if (timeout == 0) {
                    //success! we transmitted
                    collisions = 0
                    emitting = false
                    timeout = nextEmitting()

                    stats.succeses++
                } else {
                    //if we're still transmitting
                    if (incomingSignals.size > 1) {
                        //we detect collision!
                        collisions++
                        collision = true

                        stats.collisions++
                    }
                }
            }
        } else {
            //not emitting = no collision
            if (timeout == 0) {
                //we want to emit
                if (incomingSignals.size == 0) {
                    //if the line's free, we start emitting
                    emitting = true
                    //this is enough, because we're on a ring
                    timeout = maxTimeout + 1

                    stats.tries++
                } else {
                    //else, we wait some more
                    timeout = Random.nextInt() % maxTimeout

                    stats.waits++
                }
            }
        }
        timeout--
        signals.clear()
        signals.addAll(incomingSignals)
        incomingSignals.clear()
    }

    override fun signal(signal: Signal) {
        //we don't want to transmit signals from ourselves father - they've made the full loop
        if (signal.of != this && signal.prev != this) incomingSignals.add(signal)
    }

    private fun nextEmitting(): Int {
        var i = 1
        while (Random.nextDouble() > probability) i++
        return i
    }
}