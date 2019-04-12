const express = require('express');
const path = require('path');

const app = express();

app.use(express.static(path.join(__dirname, 'static')));

app.listen(2137);

console.log(`Check http://localhost:2137/`);