package graph.nodes

import graph.signals.Signal
import java.lang.Math.abs
import kotlin.random.Random

class EmitterNode(id: String, private val maxTimeout: Int) : AbstractNode(id) {
    private var collisions = 0
    private var timeout = Random.nextInt() % maxTimeout

    public var emitting = false
        private set
    public var collision = false
        private set

    override fun step() {
        //timeout controls both emitting and waiting to emit
        if (timeout-- == 0) {
            //if we're emitting, stop it
            if (emitting) {
                emitting = false
                if (collision) {
                    timeout = (0b1 shl (abs(Random.nextInt()) % collisions)) * maxTimeout
                    //println("$this stops emitting and waits $timeout")
                } else {
                    collisions = 0
                    //println("$this successfully transmitted a message")
                }
            }
            //if there are no signals, we can start emitting
            if (signals.isEmpty()) {
                //println("$this starts emitting")
                emitting = true
                timeout = 2 * maxTimeout
            } else {
                timeout = abs(Random.nextInt()) % maxTimeout
                //println("$this attempts to emit, but line is taken, waits $timeout")
            }
        }
        if (emitting) signals.add(Signal(this, this))
        super.step()
    }

    override fun signal(signal: Signal) {
        if (signal.of == this) return
        super.signal(signal)
        //if we detect more than a single signal
        if (emitting && signals.size > 1 && !collision) {
            collision = true
            collisions++
            //println("$this detects a collision")
        }
    }
}