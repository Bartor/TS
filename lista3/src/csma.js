class CSMA {
    constructor(size) {
        this.line = new Array(size);
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
    for (let t of this.line) {
        
    }
    for (let n of this.nodes) {

    }
}