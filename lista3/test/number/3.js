let CSMA = require('../csma-testable');

let SIZE = 100;

let c = new CSMA(SIZE);

c.addNode('A', 0, 0.05);
c.addNode('B', Math.floor(SIZE/12), 0.05);
c.addNode('C', Math.floor(SIZE/12*2), 0.05);
c.addNode('D', Math.floor(SIZE/12*3), 0.05);
c.addNode('E', Math.floor(SIZE/12*4), 0.05);
c.addNode('F', Math.floor(SIZE/12*5), 0.05);
c.addNode('G', Math.floor(SIZE/12*6), 0.05);
c.addNode('H', Math.floor(SIZE/12*7), 0.05);
c.addNode('I', Math.floor(SIZE/12*8), 0.05);
c.addNode('J', Math.floor(SIZE/12*9), 0.05);
c.addNode('K', Math.floor(SIZE/12*10), 0.05);
c.addNode('L', Math.floor(SIZE/12*11), 0.05);
c.addNode('M', SIZE - 1, 0.05);

let results = {
    attempts: 0,
    successes: 0,
    collisions: 0,
    waitsToStart: 0,
    multiCollisions: 0
};

for (let i = 0; i < SIZE*100; i++) {
    let res = c.step();
    for (let r of res) {
        switch (r.event) {
            case 'wait': results.waitsToStart++; break;
            case 'start': results.attempts++; break;
            case 'collision': results.collisions++; break;
            case 'prolonged': results.multiCollisions++; break;
            case 'success': results.successes++; break;
        }
    }
}

console.log(results);