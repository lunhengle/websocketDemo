<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/12
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello world</title>
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.min.js"></script>
    <script src="http://cdn.bootcss.com/stomp.js/2.3.3/stomp.js"></script>
    <script type="text/javascript">
        var stompClient = null;

        /**
         * 连接.
         */
        function connectAny() {
            var socket = new SockJS('/stomp');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected======' + frame);
                stompClient.subscribe('/app/greeting', function (greeting) {
                    var data = JSON.parse(greeting.body);
                    console.log("topic======" + data.content);
                    showGreeting(data.content);
                });
                stompClient.subscribe('/topic/lun', function (greeting) {
                    var data = JSON.parse(greeting.body);
                    console.log("topic lun======" + data.content);
                    showGreeting(data.content);
                });
            });
        }

        function connect() {
            var userId = document.getElementById("name").value;
            var socket = new SockJS('/stomp');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected======' + frame);
                stompClient.subscribe('/user/' + userId + '/message', function (greeting) {
                    var data = JSON.parse(greeting.body);
                    console.log("user======" + data.content);
                    showGreeting(data.content);
                });
            });
        }

        function sendAny() {
            var userId = document.getElementById("name").value;
            stompClient.send("/app/hello", {}, JSON.stringify({name: userId}));
        }
        function send() {
            var userId = document.getElementById("name").value;
            stompClient.send("/app/message", {}, JSON.stringify({name: userId}));
        }
        function showGreeting(message) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            response.appendChild(p);
        }
    </script>
</head>
<body>
<div>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="connectAny" onclick="connectAny();">ConnectAny</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
    </div>
    <div id="conversationDiv">
        <label>What is your name?</label><input type="text" id="name"/>
        <input id="send" onclick="send()" type="button" value="send"/>
        <input id="sendAny" onclick="sendAny()" type="button" value="sendAny"/>
        <p id="response"></p>
    </div>
</div>
</body>
</html> 