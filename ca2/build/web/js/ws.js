/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function(){
    var socket; 
    
    $("#start").on("click", function(){
        if(!socket){
            socket = new WebSocket("ws://" + document.location.host + getContectPath() + "notews/all");
               socket.onopen = function() {
                console.log("connected");
                socket.send("getAllNotes");
            }

            socket.onclose = function() {
                console.log("disconnected");
            }

            socket.onmessage = function(evt) {
                var data = JSON.parse(evt.data);
                console.log("start");
                $.each(data, function(index, value){
                    $("input[name='catIds[]']").each( function () {
                        if ($(this).is(":checked") && $(this).val() == value.category) {
                            var html = "";
                            html += "<tr>";
                            html += "<td>"+value.title+"</td>";
                            html += "<td>"+value.create_date+"</td>";
                            html += "<td>"+value.category+"</td>";
                            html += "<td>"+value.content+"</td>";
                            html += "</tr>";
                            $("#notes > tbody > tr:first").before(html);
                        }
                    })
                })
            }

        }
    });

});

function getContectPath() {
    var path = document.location.pathname;
    var contextPath = path;
    if (path.indexOf("face") > 0) {
        contextPath = path.substring(0, path.indexOf("face"));
    }
    return contextPath;
}

function startSocket(){

    
}