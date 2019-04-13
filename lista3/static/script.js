window.addEventListener('load', () => {
    document.getElementById('encode').addEventListener('click', encode);
    document.getElementById('decode').addEventListener('click', decode);
});

function encode() {
    let t = document.getElementById('decoded').value;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        document.getElementById('encoded').value = this.responseText;
    }
    xhr.open('GET', `/e/${t}`, true);
    xhr.send();
}

function decode() {
    let t = document.getElementById('encoded').value;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        document.getElementById('decoded').value = this.responseText || 'error';
    }
    xhr.open('GET', `/d/${t}`, true);
    xhr.send();
}