let CSMA = require('../csma-testable');

let SIZE = 100;

let c = new CSMA(SIZE);

c.addNode('A', 0, 0.01);
c.addNode('B', Math.floor(SIZE/2), 0.01);
c.addNode('C', SIZE - 1, 0.01);

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