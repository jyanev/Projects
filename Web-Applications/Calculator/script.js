var screenText = document.getElementById("screen-text");

var clear  = document.getElementById("clear");
var del    = document.getElementById("delete");
var div    = document.getElementById("div");
var mult   = document.getElementById("mult");
var minus  = document.getElementById("min");
var plus   = document.getElementById("plus");
var equals = document.getElementById("equals");
var zero   = document.getElementById("zero");
var one    = document.getElementById("one");
var two    = document.getElementById("two");
var three  = document.getElementById("three");
var four   = document.getElementById("four");
var five   = document.getElementById("five");
var six    = document.getElementById("six");
var seven  = document.getElementById("seven");
var eight  = document.getElementById("eight");
var nine   = document.getElementById("nine");

var currentValue;
var newValue;
var operation;
var isLeftValue = true;
var isEquatable = false;
var toClear = false;

function updateScreenText(value) {
    if (toClear) {
        screenText.textContent = 0;
        toClear = false;
    }
    if (screenText.textContent === "0") {
        screenText.textContent = value;
    } else {
        if (isLeftValue) {
            screenText.textContent += value;
        } else {
            screenText.textContent = value;
            isLeftValue = true;
        }
    }
    newValue = parseInt(screenText.textContent);
}

function clearScreen() {
    screenText.textContent = 0;
    currentValue = 0;
    newValue = 0;
    operation = "";
    isLeftValue = true;
    isEquatable = false;
}

function popLast() {
    if (toClear) {
        screenText.textContent = 0;
        toClear = false;
    }
    screenText.textContent = screenText.textContent.slice(0, -1);
    if (screenText.textContent.length === 0) {
        screenText.textContent = 0;
    }
    newValue = parseInt(screenText.textContent);
}

function updateOperation(value) {
    if (isEquatable) {
        currentValue = eval(currentValue + operation + newValue);
        screenText.textContent = currentValue;
    } else {
        isEquatable = true;
        currentValue = newValue;
    }
    operation = value;
    isLeftValue = false;
}

function equate() {
    screenText.textContent = eval(currentValue + operation + newValue);
    toClear = true;
    currentValue = 0;
    newValue = 0;
    operation = "";
    isLeftValue = true;
    isEquatable = false;
}

