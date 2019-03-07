function getRndInteger(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

for (var i = 0; i < getRndInteger(3, 5); i++) {
    var elem = document.createElement("LI");
    var subElem = document.createTextNode("Item");
    elem.appendChild(subElem);
    document.getElementById("FirstElement").appendChild(elem);
}

for (var i = 0; i < getRndInteger(3, 5); i++) {
    var elem = document.createElement("LI");
    var subElem = document.createTextNode("Item");
    elem.appendChild(subElem);
    document.getElementById("SecondElement").appendChild(elem);
}

for (var i = 0; i < getRndInteger(3, 5); i++) {
    var elem = document.createElement("LI");
    var subElem = document.createTextNode("Item");
    elem.appendChild(subElem);
    document.getElementById("ThirdElement").appendChild(elem);
}

for (var i = 0; i < getRndInteger(3, 5); i++) {
    var elem = document.createElement("LI");
    var subElem = document.createTextNode("Item");
    elem.appendChild(subElem);
    document.getElementById("FourthElement").appendChild(elem);
}

for (var i = 0; i < getRndInteger(3, 5); i++) {
    var elem = document.createElement("LI");
    var subElem = document.createTextNode("Item");
    elem.appendChild(subElem);
    document.getElementById("FifthElement").appendChild(elem);
}
