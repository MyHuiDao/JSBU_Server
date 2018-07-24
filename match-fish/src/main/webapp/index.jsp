<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.min.js"></script>
<title>websocket client</title>
</head>
<script type="text/javascript">
var url1=window.location.host;
var wsUrl="ws://"+url1+"/match-fish/v1/game/";
var code="<%=request.getParameter("token")%>";
wsUrl+=code;
var ws;
function hello(){
    ws = new WebSocket(wsUrl);
    ws.onopen = function(evn){
    	//sendPing();
		  var dv = document.getElementById("dv");
        dv.innerHTML+="打开连接成功</br>";

    };
    ws.onmessage = function(evn){
    	console.log(typeof evn.data);
    	if(typeof evn.data =="string"){
	        var dv = document.getElementById("dv");
	        dv.innerHTML+=evn.data+"</br>";
    	}
    };
    ws.onclose = function(){
        var dv = document.getElementById("dv");
        dv.innerHTML+="连接已关闭</br>";
    };
    ws.onerror=function(){
    	
    };
    
};
function subsend(){
    var msg = document.getElementById("msg").value;
	    var code = document.getElementById("code").value;
	var msg='{"code":"'+code+'","data":"'+msg+'"}';
    ws.send(msg);
}
/* function sendPing(){
	var ab=new ArrayBuffer(1);
	ws.send(ab);
	setTimeout(function(){
		sendPing();
	}, 3000);
} */

hello();

window.onkeydown=function(e){
	if(e.keyCode==13){
		subsend();
	}
}

function clear1(){
	var dv = document.getElementById("dv");
        dv.innerHTML="";
}

function subsend() {
	var msg = document.getElementById("msg").value;
	var code = document.getElementById("code").value;
	try {
		msg = '{"code":"' + code + '","data":' + JSON.stringify(msg) + '}';
	} catch (e) {
		msg = '{"code":"' + code + '","data":' + msg + '}';
	}

	ws.send(msg);
}
</script>
<body>
    <div id="dv"></div>
 code:<input type="text" id ="code" />content:<input type="text" id ="msg" /><input type="button" onclick="clear1()" value="清空" /><input type="button" onclick="subsend()" value="发送" />
</body>
</html>

