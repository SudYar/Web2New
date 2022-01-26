
document.querySelector("#y-text").onkeyup = validateY;

function validateY(){
    this.value = this.value.replace(/\,/g, ".").replace(/[^\d\.\-]/g, "");
    while (this.value.lastIndexOf('-') > 0) {
        this.value = this.value.substr(0, this.value.lastIndexOf('-'));
    }
    var y = parseFloat(this.value);
    while (y >= 5 || y <= -3) {
        this.value = this.value.substr(0, this.value.length - 1);
        y = parseFloat(this.value);
    }
    while ((this.value[0] == '-' && (this.value[1] == '.' || this.value.lastIndexOf('.') > 2)) || (this.value[0] != '-' && (this.value[0] == '.' || this.value.lastIndexOf('.') > 1))) {
        this.value = this.value.substr(0, this.value.lastIndexOf('.'));
    }
}

$("#request-coordinates").submit(function( event ) {
    var newURL = location.href.split("?")[0];
    window.history.pushState('object', document.title, newURL);
    event.preventDefault();
    if ($("#request-coordinates").valid()){
        getServletContext().setAttribute("results", results);
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
});


function clearAll(){
    $.ajax({
        url: "controller",
        type: "POST",
        data: "do=clear",
        beforeSend: function () {

        },
        success: function (data) {
            document.innerHTML = data; //устанавливаю принятый html
            location.reload();
        }
    });
}