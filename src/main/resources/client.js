'use strict';

var usernameForm = document.querySelector("#usernameForm");
var usernameInput = document.querySelector("#usernameInput");
var buttonConnect = document.querySelector("#connectButton");

usernameInput.value = "chipchip"

var stompClient = null;
var username = null;

function connect(event) {
    username = usernameInput.value;
    if(username) {
      //var ws = new SockJS('http://localhost:8080/api/auth/ws');
         const ws = new WebSocket('http://192.168.1.76:8080/ws');
      ws.onopen = () => {
        ws.send("hello client ket noi server"); 
      };
      
      ws.onmessage = e => {
        // a message was received
        console.log(e.data);
      };
      
      ws.onerror = e => {
        console.log("khong the dau");
        console.log(e.message);
      };
      
      ws.onclose = e => {
        // connection closed
        console.log(e.code, e.reason);
      };
    }
    
    event.preventDefault();
}

usernameForm.addEventListener('submit', connect, true)