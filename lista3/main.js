let frame = require('./frame');
let config = JSON.parse(require('fs').readFileSync('./config.json'));

let exampleInput = '11111111101011111111110111111101111';

console.log(`Input: ${exampleInput}`);

let x = frame.encode(exampleInput, config);

console.log(`Encoded: ${x}`);

console.log(`Decoded: ${frame.decode(x, config)}`);