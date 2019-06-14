package graph.nodes

import graph.signals.Signal
import java.lang.Math.abs
import kotlin.random.Random

class EmitterNode(id: String, private val maxTimeout: Int) : AbstractNode(id) {
    private var collisions = 0
    private var timeout = Random.nextInt() % maxTimeout
    private var emitting = false

    override fun step() {
        //timeout controls both emitting and waiting to emit
        if (timeout-- == 0) {
            //if we're emitting, stop it
            if (emitting) {
                emitting = false
                collisions = 0
            }
            //if there are no signals, we can start emitting
            if (signals.isEmpty()) {
                println("$this starts emitting")
                emitting = true
                timeout = 2*maxTimeout
            } else {
                timeout = abs(Random.nextInt()) % maxTimeout
            }
        }
        if (emitting) signals.add(Signal(this, this))
        super.step()
    }

    override fun signal(signal: Signal) {
        if (signal.of == null && emitting) {
            println("$this detects collision")
            emitting = false
            collisions++
            timeout = (1 shl (abs(Random.nextInt()) % collisions))*maxTimeout
        }

        super.signal(signal)
        //if we detect more than a single signal
        if (signals.size > 1) {
            //we clear all signals
            signals.clear()
            //and add an error signal
            signals.add(Signal(null, this))
        }
    }
}