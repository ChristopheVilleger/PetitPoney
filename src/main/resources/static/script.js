jQuery( document ).ready(function( $ ) {
    $.get("http://localhost:8080/phone", function(data, status){
        $("#phoneStolen").html(JSON.stringify(data));
    });
});


