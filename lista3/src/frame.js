const crc = require('crc');

const encode = (data = '', config = {escSeq: '01111110', escNum: 5}) => {
    data = data + crc.crc32(data).toString(2).padStart(32, '0');
    let outputData = config.escSeq;
    let oneCounter = 0;

    //WE DID IT REDDIT
    for (let c of data) outputData += c + (((oneCounter = (oneCounter + (c === '1' ? 1 : -oneCounter))) === config.escNum) ? ((oneCounter = 0) === 0 ? '0': 'this') : '');
    //this loop is a joke, don't actually code like this

    outputData += config.escSeq;
    return outputData;
}

const decode = (data = '', config = {escSeq: '01111110', escNum: 5}) => {
    if (!data.startsWith(config.escSeq) || !data.endsWith(config.escSeq)) throw 'incorrect coding';

    data = data.slice(config.escSeq.length).slice(0, -config.escSeq.length);

    let outputData = '';
    let oneCounter = 0, delFlag = false;

    //WE DID IT AGAIN REDDIT
    for (let c of data) outputData += ((oneCounter = (oneCounter + (c === '1' ? 1 : -oneCounter))) === config.escNum) ? ((delFlag = true) === true && (oneCounter -= config.escNum) === 0 ? c : 'is a') : (delFlag ? ((delFlag = false) === false ? '' : 'joke code') : c);
    //this loop is an even bigger joke

    if (outputData.slice(-32) !== crc.crc32(outputData.slice(0, -32)).toString(2).padStart(32, '0')) throw 'incorrect crc';

    return outputData.slice(0, -32);
}

module.exports = {
    encode: encode,
    decode: decode
}