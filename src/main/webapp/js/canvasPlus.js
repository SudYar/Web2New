
const graph = document.getElementById("graph");
const cont = graph.getContext('2d');
const height = 250;
const weight = 250;
const countXY = 5;
const unit = (height / (2 * countXY)) * 0.9;

window.onload = function() {
    reDraw();
}


$("input[name='r-value']").change(function (){
    reDraw();
    $('#r-error').css({"visibility":"hidden"});
});

document.getElementById("reset_button").onclick = function (){
    clear();
    drawGrid(cont, weight/2, height/2);
    drawDots();
};

function getR(){
    var R = document.querySelector('input[name="r-value"]:checked');

    if (R != null) return R.value;
    else return 0;
}

function setX(x){
    let arrayOfX = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
    let res;
    if (x < -3){
        res = -3;
    }else if (x > 5){
        res = 5;
    }else {
        res = arrayOfX[0];
        for (const i of arrayOfX) {
            if (Math.abs(i-x)<Math.abs(res-x)){
                res = i;
            }
        }
    }
    let xId = 'x' + res;
    document.getElementById(xId).click();
}

function setY(y){
    if (y<-3){
        y=-2.9999;
    }else if(y>5){
        y=4.9999;
    }
    document.getElementById("y-text").value = y.toFixed(4);
}

function setR(r) {
    let arrayOfR = [1, 1.5, 2, 2.5, 3];
    let res;
    if (r < 1){
        res = arrayOfR[0];
    }else if (r > 3){
        res = arrayOfR[4];
    }else {
        res = arrayOfR[0];
        for (const i of arrayOfR) {
            if (Math.abs(i-r)<=Math.abs(res-r)){
                res = i;
            }
        }
    }
    let rId = 'r' + res;
    document.getElementById(rId).click();
}

function setCoordinates(x, y) {
    if (getR() !== 0){
        setX(x);
        setY(y);
        return true;
    } else {
        alert("Сначала выберите нужный вам R")
        // $('#r-error').css({"visibility":"visible"});
        return false;
    }
}

//мы определяем координаты клика на canvas
function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: convertXtoUnits(evt.clientX - rect.left),
        y: convertYtoUnits(evt.clientY - rect.top)
    };
}
function convertXtoUnits(x1){
    return (x1 - weight/2 )/( weight * 0.9/ (2*countXY));
}
function convertYtoUnits(y1){
    return (height/2 - y1)/(height * 0.9 / (2*countXY));
}

graph.addEventListener('click', function(evt) {
    var mousePos = getMousePos(graph, evt);
    if (setCoordinates(mousePos.x, mousePos.y)) {
        var newURL = location.href.split("?")[0];
        window.history.pushState('object', document.title, newURL);
        evt.preventDefault();
        if ($("#request-coordinates").valid()) {
            var formData = $('#request-coordinates').serialize();
            console.log(formData);
            $.ajax({
                url: "controller",
                type: "POST",
                data: formData,
                beforeSend: function () {

                },
                success: function (data) {
                    document.innerHTML = data; //устанавливаю принятый html
                    location.reload();
                }
            });
        }
    }
}, false);




function clear(){
    cont.fillStyle = "#FFFFFF";
    cont.fillRect(0,0, weight, height);
}

function reDraw(){

    const x0 = weight / 2;
    const y0 = height/2;
    let r = getR();


    clear();

    r*=unit;
    cont.fillStyle="#3974af";
// прямоугольник
    cont.fillRect(x0,y0-r,r/2,r);

// треугольник
    cont.beginPath();
    cont.moveTo(x0, y0);
    cont.lineTo(x0, y0-r);
    cont.lineTo(x0-r/2, y0);
    cont.fill();

    // четверть круга
    cont.beginPath();
    cont.arc(x0, y0, r/2, 0  , Math.PI * 1/2);
    cont.lineTo(x0, y0);
    cont.fill();

    console.log("Рисую сетку");
    drawGrid(cont, x0, y0);
    drawDots();
}

function drawGrid(cont, x, y) {
    cont.fillStyle = "#000000";
    cont.beginPath();
    cont.moveTo(0, y);
    cont.lineTo(weight, y);
    cont.moveTo(x, 0);
    cont.lineTo(x, height);
    cont.closePath();
    cont.stroke();
    cont.font = '20px serif';
    //проставляем числа над осями
    for (let i = -countXY; i<=countXY; i++){
        if (i<=0){
            cont.fillText(i, x+i*unit-10, y+15);
        }else {
            cont.fillText(i, x+i*unit-5, y+15);
        }
        if(i===0){continue;}
        cont.fillText(i, x+2, y-i*unit+5);
    }
}

function drawDots(){
    for (let i = 0; i < dots.length; i++) {
        drawOneDot(dots[i]);
    }
}

function drawOneDot(dot){
    let dotX = dot.x * unit + weight/2;
    let dotY = height/2 - dot.y * unit;

    cont.fillStyle = "red";
    cont.beginPath();
    cont.arc(dotX, dotY, 2.5, 0 , Math.PI * 2);
    cont.fill();
}