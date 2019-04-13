const express = require('express');
const path = require('path');

const frame = require('./frame');

const app = express();

app.use(express.static(path.join(__dirname, 'static')));

//encoding endpoints
app.get('/e/:code', (req, res) => {
    res.send(frame.encode(req.params.code));
});

app.get('/d/:code', (req, res) => {
    let r = '';
    try {
        r = frame.decode(req.params.code);
    } catch (e) {}
    res.send(r);
});

app.listen(2137);

console.log(`Check http://localhost:2137/`);