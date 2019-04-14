/***
 * Modified version of CSMA to allow easier results gathering
 */

/***
 * A transmission line with nodes emitting stuff
 * @param {number} size - Size of the transmission line
 */
class CSMA {
    constructor(size) {
        this.line = [];
        while (size --> 0) this.line.push([]);
        this.nodes = [];
        this.verbose = false;
    }
}

/***
 * Adds a new node
 * @param {object} id - A unique identifier
 * @param {number} position - A unique Position on the transmission line
 * @param {number} probability - How probable is it to start emitting at any given time
 */
CSMA.prototype.addNode = function (id, position, probability = 0.05) {
    if (position >= this.line.length) throw 'wrong position';
    if (this.nodes.some(e =>(e.id == id || e.position == position))) throw 'this id or position is taken';
    let t = 0;
    while (Math.random() > probability) t++;
    this.nodes.push({
        id: id,
        position: position,
        emit: false,
        collision: false,
        timeout: t,
        probability: probability,
        mult: 1
    }
    );
    if (this.verbose) console.log(`Node ${id} is added at ${position}`);
}

/***
 * Performs a single step, simulating the "network"
 */
CSMA.prototype.step = function () {
    let results = [];

    //t is an array of arrays
    //each cell holds a list of transmissions currently being send through
    //a transmission is an object {d: -1|0|1, id: id of a node}

    let newLine = [];

    let i = this.line.length;
    while (i --> 0) newLine.push([]);

    for (let [i, l] of this.line.entries()) {
        for (let t of l) {
            if (t.d === -1) {
                if (this.verbose) console.log(`<-[${t.id}]`);
                if (i !== 0) newLine[i-1].push({d: -1, id: t.id});
            }

            if (t.d === 0) {
                if (this.verbose) console.log(`<-[${t.id}]->`);
                if (i !== 0) newLine[i-1].push({d: -1, id: t.id});
                if (i !== this.line.length-1) newLine[i+1].push({d: 1, id: t.id});
            }

            if (t.d === 1) {
                if (this.verbose) console.log(`[${t.id}]->`);
                if (i !== this.line.length-1) newLine[i+1].push({d: 1, id: t.id});
            }
        }
    }

    this.line = newLine;

    for (let n of this.nodes) {
        if (!n.emit && n.timeout === 0) {
            if (this.line[n.position].length > 0) {
                results.push({
                    id: n.id,
                    event: "wait"
                });

                n.timeout += Math.floor(this.line.length/2);
                if (this.verbose) console.log(`${n.id} waits for line to be clear`);
            } else {
                results.push({
                    id: n.id,
                    event: "start"
                })
                if (this.verbose) console.log(`${n.id} is starting to emit`);
                n.timeout = 2*this.line.length + 1;
                n.emit = true;            
            }
        }

        if (n.emit) {
            if (this.verbose) console.log(`${n.id} emits`);

            this.line[n.position].push({d: 0, id: n.id});
            if (!n.collision && this.line[n.position].filter(e => e.id != n.id).length) {
                results.push({
                    id: n.id,
                    event: "collision"
                });

                if (this.verbose) console.log(`${n.id} detected collision`);
                n.collision = true;
            }
        }

        n.timeout--;

        if (n.emit && n.timeout === 0) {
            n.emit = false;

            if (n.collision) {
                let timeouts = [];
                for (let i = 0; i <= n.mult; i++) timeouts.push(Math.pow(2, i));
                n.timeout = this.line.length * timeouts[Math.floor(Math.random()*timeouts.length)];                
                n.mult++;
                n.collision = false;
                results.push({
                    id: n.id,
                    event: "prolonged"
                });
                if (this.verbose) console.log(`${n.id} waits ${n.timeout}`);
            } else {
                n.mult = 1;
                results.push({
                    id: n.id,
                    event: "success"
                })
                while (Math.random() > n.probability) n.timeout++;
                if (this.verbose) console.log(`${n.id} successfully transmitted`);
            }
        }
    }

    return results;
}

module.exports = CSMA;