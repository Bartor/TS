class CSMA {
    constructor(size) {
        this.line = [];
        while (size --> 0) this.line.push([]);
        this.nodes = [];
        this.verbose = false;
    }
}

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
    return this.nodes[this.nodes.length - 1];
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
                n.timeout += Math.floor(this.line.length/2);
                if (this.verbose) console.log(`${n.id} waits for line to be clear`);
            } else {
                if (this.verbose) console.log(`${n.id} is starting to emit`);
                this.emit(n.id);
            }
        }

        if (n.emit) {
            if (this.verbose) console.log(`${n.id} emits`);

            this.line[n.position].push({d: 0, id: n.id});
            if (!n.collision && this.line[n.position].filter(e => e.id != n.id).length) {
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
                if (this.verbose) console.log(`${n.id} waits ${n.timeout}`);
            } else {
                n.mult = 1;
                while (Math.random() > n.probability) n.timeout++;
                if (this.verbose) console.log(`${n.id} successfully transmitted`);
            }
        }
    }
}

CSMA.prototype.emit = function(id) {
    let n = this.nodes.find(e => e.id === id);
    if (n === undefined) throw 'no such node exists';
    if (n.emit) throw 'this node is already emitting';

    n.timeout = 2*this.line.length + 1;
    n.emit = true;
}

function initCsma(element, csma) {
    let tab = document.createElement('table');
    let line = document.createElement('tr');
    let nodes = document.createElement('tr');
    for (let e of csma.line.entries()) {
        let lineCell = document.createElement('td');
        let nodeCell = document.createElement('td');

        let input = document.createElement('input');
        input.type = 'text';

        let button = document.createElement('input');
        button.type = 'button';
        button.value = 'add node'

        nodeCell.appendChild(input);
        nodeCell.appendChild(button);

        button.addEventListener('click', addNode(csma, tab));

        line.appendChild(lineCell);
        nodes.appendChild(nodeCell);
    }

    tab.appendChild(line);
    tab.appendChild(nodes);

    tab.id = 'csma';

    let stepButton = document.createElement('input');
    stepButton.type = 'button';
    stepButton.value = 'step';

    stepButton.addEventListener('click', nextStep(csma, tab));

    element.appendChild(tab);
    element.appendChild(stepButton);
}

function addNode(csma) {
    return function(event) {
        let id = event.target.parentNode.children[0].value;
        let position = event.target.parentNode.cellIndex;

        if (id !== '') {
            let added = csma.addNode(id, position);

            let parent = event.target.parentNode;
            while (parent.firstChild) parent.removeChild(parent.firstChild);
    
            parent.textContent = `${id}\r\n${added.timeout}`;
        }
    }
}

function nextStep(csma, tab) {
    return function() {
        csma.step();
        for (let [i, l] of csma.line.entries()) {
            tab.children[0].children[i].textContent = '';
            for (let e of l) {
                tab.children[0].children[i].textContent += `${e.id}\r\n`;
            }
        }
        
        for (let n of csma.nodes) {
            tab.children[1].children[n.position].textContent = `${n.id}\r\n${n.timeout}`;
        }
    }
}