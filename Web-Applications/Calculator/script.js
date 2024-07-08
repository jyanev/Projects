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


var leftVal = "0";
var rightVal = "0";
var newVal = false;
var operation = "";
var isEquatable = false;


function clearScreen() {

    leftVal = 0;
    rightVal = 0;
    newVal = false;
    operation = "";
    isEquatable = false;
    screenText.textContent = "0";

}


function updateScreenText(value) {

    if (screenText.textContent === "0" || newVal) {
        screenText.textContent = value;
        newVal = false;
    } else {
        screenText.textContent += value;
    }

    if (isEquatable) { 
        rightVal = parseInt(screenText.textContent);
    } else {             
        leftVal  = parseInt(screenText.textContent); 
    }

}


function deleteLast() {

    screenText.textContent = screenText.textContent.slice(0, -1);
    if (screenText.textContent.length === 0) {
        screenText.textContent = 0;
    }
    result = parseInt(screenText.textContent);
    if (isEquatable) { 
        rightVal = parseInt(screenText.textContent);
    } else {             
        leftVal  = parseInt(screenText.textContent); 
    }


}


function updateOperation(value) {

    if (isEquatable) {
        leftVal = eval(leftVal + (operation) + rightVal);
        newVal = true;
        updateScreenText(leftVal.toString());
        newVal = false;
        rightVal = 0;
    }
    newVal = true;
    isEquatable = true;
    operation = value;

}

function equate() {
    if (isEquatable) {
        leftVal = eval(leftVal + (operation) + rightVal);
        newVal = true;
        updateScreenText(leftVal.toString());
        newVal = false;
        rightVal = 0;
        isEquatable = false;
    }
}