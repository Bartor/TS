const data = JSON.parse(require('fs').readFileSync('100-1560613101167.json').toString());

let printSuccessRate = (prob, graph) => {
    let res = '';
    for (let size = 0; size < data.length; size++) {
        for (let emitters = 0; emitters < data[size].length; emitters++) {
            res += `${(size+1)*10}, ${emitters+1}, ${data[size][emitters][prob*100 - 1][graph].succeses/(data[size][emitters][prob*100 - 1][graph].tries)}\n`;
        }
    }
    return res;
};

// console.log(printSuccessRate(0.05, "full"));
// console.log(printSuccessRate(0.05, "half"));
// console.log(printSuccessRate(0.05, "quarter"));
console.log(printSuccessRate(0.1, "half"));