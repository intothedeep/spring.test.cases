<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String root = request.getContextPath();%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<c:set var="root" value="+{pageContext.request.contextPath}"/>  

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  	
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<!-- include summernote css/js-->
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.js"></script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>

	<title>Chat WebSocket</title>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
	<script type="text/javascript">
			var root = "<%=root%>";
            var stompClient = null;
             
            function setConnected(connected) {
                document.getElementById('connect').disabled = connected;
                document.getElementById('disconnect').disabled = !connected;
                document.getElementById('conversationDiv').style.visibility 
                  = connected ? 'visible' : 'hidden';
                document.getElementById('response').innerHTML = '';
            }
             
            function connect() {
                var socket = new SockJS(root + '/chat');
                stompClient = Stomp.over(socket);  
                stompClient.connect({}, function(frame) {
                    setConnected(true);
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/messages', function(messageOutput) {
                        showMessageOutput(JSON.parse(messageOutput.body));
                    });
                });
            }
             
            function disconnect() {
                if(stompClient != null) {
                    stompClient.disconnect();
                }
                setConnected(false);
                console.log("Disconnected");
            }
             
            function sendMessage() {
                var from = document.getElementById('from').value;
                var text = document.getElementById('text').value;
                stompClient.send("/app/chat", {}, 
                  JSON.stringify({'from':from, 'text':text}));
            }
             
            function showMessageOutput(messageOutput) {
                var response = document.getElementById('response');
                var p = document.createElement('p');
                p.style.wordWrap = 'break-word';
                p.appendChild(document.createTextNode(messageOutput.from + ": " 
                  + messageOutput.text + " (" + messageOutput.time + ")"));
                response.appendChild(p);
            }
        </script>
	</head>
	<body onload="disconnect()">
        <div class="container col-sm-6 col-sm-offset-3">
				
			<div class="form-group">
			  <label class="control-label"> 닉네임을 설정하세요! </label>
			  <div class="input-group">
			    <input type="text" class="form-control" id="from" placeholder="Choose a nickname">
			    <span class="input-group-btn">
		        	<button id="connect" class="btn btn-warning" onclick="connect();">Connect</button>
			    </span>
			    
			    <span class="input-group-btn">
	                <button id="disconnect" class="btn btn-warning" disabled="disabled" onclick="disconnect();"> Disconnect </button>
			    </span>
			    
			   </div>
			</div>
				

            <div>
            </div>

            <div id="conversationDiv">
				<div class="form-group">
				  <div class="input-group">
				    <input type="text" class="form-control" id="text" placeholder="Write a message...">
				    <span class="input-group-btn">
							<button id="sendMessage" class="btn btn-info" onclick="sendMessage();">Send</button>
				    </span>
				  </div>
				  <p id="response"></p>
				</div>				
			</div>
			
        </div>
 
    </body>
</html>