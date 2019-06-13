require('http').createServer((req, res) => {
    res.write(req.rawHeaders.map((e, i, a) => !(i % 2) ? `${e}: ${a[i+1]}` : '\n').join(''));
    res.end();
}).listen(2137);