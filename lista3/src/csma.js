class CSMA {
    constructor(size) {
        this.line = [];
        while (size --> 0) this.line.push([]);
        this.nodes = [];
    }
}

CSMA.prototype.addNode = function (id, position) {
    if (position >= this.line.length) throw 'wrong position';
    if (this.nodes.some(e =>(e.id == id || e.position == position))) throw 'this id or position is taken';
    this.nodes.push({
        id: id,
        position: position,
        emit: false,
        timeout: -1
    }
    );
}

CSMA.prototype.step = function () {
    //t is an array of arrays
    //each cell holds a list of transmissions currently being send through
    //a transmission is an object {d: -1|0|1, id: id of a node}

    let newLine = [];

    let i = this.line.length;
    while (i --> 0) newLine.push([]);

    for (let [i, l] of this.line.entries()) {
        for (let t of l) {
            if (t.d === -1) {
                if (i !== 0) newLine[i-1].push({d: -1, id: t.id});
            }

            if (t.d === 0) {
                if (i !== 0) newLine[i-1].push({d: -1, id: t.id});
                if (i !== this.line.length-1) newLine[i+1].push({d: 1, id: t.id});
            }

            if (t.d === 1) {
                if (i !== this.line.length-1) newLine[i+1].push({d: 1, id: t.id});
            }
        }
    }

    this.line = newLine;
}