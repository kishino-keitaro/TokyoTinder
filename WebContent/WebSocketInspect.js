var webSocket;
window.onload = function() {
	var forRtoA = document.createElement('a');
	forRtoA.href = "loadMessage";
	webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://").replace(
			"https://", "wss://"));
	var messageArea = document.getElementById("messageArea");
	if (messageArea !== null) {
		messageArea.scrollTop = messageArea.scrollHeight;
	}
	var appendMessage = function(value, tag, user) {
		var messageElement = document.createElement("div");
		messageElement.className = tag;
		messageElement.innerText = value;
		messageArea.appendChild(messageElement);
		messageArea.scrollTop = messageArea.scrollHeight;
		// messageArea.insertBefore(messageElement, messageArea.firstChild);
	}
	webSocket.onopen = function() {
		// appendMessage("Opened", "blue");
	}
	webSocket.onclose = function() {
		// appendMessage("Error", "red");
	}
	webSocket.onmessage = function(message) {
		console.log(message);
		var data = JSON.parse(message.data);
		if ("message" == data.command) {
			var result = document.getElementById("messageInput").name
					.split(":");
			var id = result[0];
			console.log(id + ":" + data.user);
			if (data.user === id) {
				appendMessage(data.text, "self", id);
			} else {
				appendMessage(data.text, "pair", id);
			}
		} else if ("createMessage" == data.command) {
			$("#messageArea").empty();
			$(data.text).appendTo(messageArea);
			document.getElementById("messageArea").scrollTop = messageArea.scrollHeight;
		} else if ("judge" == data.command) {
			console.log("match : " + data.match);
			var tmp = document.getElementsByName("targetId");
			var _name = document.getElementById("r_name").innerText;
			var _age = document.getElementById("r_age").innerText;
			var _photo = document.getElementById("randomPhoto").src;
			var _targetId = tmp[0].value;
			tmp[0].value = data.user;
			document.getElementById("randomPhoto").src = data.image;
			document.getElementById("r_name").innerHTML = data.name;
			document.getElementById("r_age").innerHTML = data.age;
			$('document').ready(function() {
				$('.photo').hide();
				$('.photo').stop(true).animate({
					width : 'show'
				}, 'fast');
			});
			if (data.match != "false") {
				console.log("match!!!!!");

				var parent = document.getElementById("navHidden")
				var li_tag = document.createElement("li");
				var a_tag = document.createElement("a");
				var div1 = document.createElement("div");
				var div2 = document.createElement("div");
				var div3 = document.createElement("div");
				var div4 = document.createElement("div");
				var input1 = document.createElement("input");
				var img_tag = document.createElement("img");
				parent.insertBefore(li_tag, parent.firstChild);
				li_tag.appendChild(a_tag).appendChild(input1);
				a_tag.appendChild(div1);
				a_tag.appendChild(div2);
				div1.appendChild(img_tag);
				div2.appendChild(div3);
				div2.appendChild(div4);

				li_tag.className = "partner clearfix";
				a_tag.href = "javascript:void(0)";
				a_tag.onclick = function(){message_window(data.user,data.match);};
				input1.type = "hidden";
				input1.name = "ids";
				input1.value = data.match + ":" + _targetId;
				div1.className = "pic";
				div2.className = "text";
				div3.className = "sidename";
				div4.className = "sidemessage";
				img_tag.border = "0";
				img_tag.src = _photo;
				img_tag.width = "40";
				img_tag.height = "40";
				img_tag.alt = "photo";
				div3.innerText = _name;
				div4.innerText = "新しいマッチです！！";
				$('.partner clearfix').hide();
				$('.partner clearfix').stop(true).animate({
					width : 'show'
				}, 'fast');
			}
		} else if ("error" == data.command) {
			// appendMessage(data.text, "red");
		}
	}
	webSocket.onerror = function(message) {
		console.log("エラーだよ");
		// appendMessage(message, "red");
	}
	var messageInput = document.getElementById("messageInput");
	if (messageInput !== null) {
		messageInput.onkeypress = function(e) {
			if (13 == e.keyCode) {
				var message = messageInput.value;
				if (webSocket && "" != message) {
					var talkRoom = messageInput.name;
					webSocket.send("message:" + talkRoom + message);
					messageInput.value = "";
				}
			}
		}
	}
	var profid;
	var profsex;
	var proftargetId;
	$('#good').on(
			'click',
			function(event) {
				profid = document.getElementsByName("UserID");
				profsex = document.getElementsByName("UserSex");
				proftargetId = document.getElementsByName("targetId")
				webSocket.send("judge:good:" + profid[0].value + ":"
						+ profsex[0].value + ":" + proftargetId[0].value);
			});
	$('#bad').on(
			'click',
			function(event) {
				profid = document.getElementsByName("UserID");
				profsex = document.getElementsByName("UserSex");
				proftargetId = document.getElementsByName("targetId")
				webSocket.send("judge:bad:" + profid[0].value + ":"
						+ profsex[0].value + ":" + proftargetId[0].value);
			});
}
function changeMessage(u_id,m_id){
	webSocket.send("createMessage:" + u_id + ":" + m_id);
}