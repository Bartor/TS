const pages = new Map();

with(require('fs')) {
    readdirSync('static').map((e) => pages.set(e, readFileSync(`static/${e}`).toString()));
}
//hyper-primitive http server
require('http').createServer((req, res) => {
    if (req.url === '/') {
        let index = pages.get('index.html');
        res.write(index !== undefined ? index : 'No index page');
    } else {
        if (req.url.indexOf(".") == -1) req.url += '.html';
        let page = pages.get(req.url.slice(1));
        res.write(page !== undefined ? page : '404 No Such Page');
    }
    res.end();
}).listen(2137);