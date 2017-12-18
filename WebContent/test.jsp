<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="models.MessageEntity"%>
<%@ page import="models.ProfileEntity"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>chat</title>
<script type="text/javascript" src="WebSocketInspect.js"></script>
</head>
<body>
	<div class="wrapper">
		<div id="messageArea">
			<%
				ProfileEntity self = (ProfileEntity) session.getAttribute("self");
				int id = self.getUserId();
				ArrayList<MessageEntity> list = (ArrayList<MessageEntity>) request.getAttribute("messageData");
				if (list != null) {
					for (MessageEntity msg : list) {
						if (msg.getSpeaker() == 0) {
			%>
			<div class="announce"><%=msg.getMessage()%></div>
			<%
				} else if (msg.getSpeaker() == id) {
			%>
			<div class="self"><%=msg.getMessage()%></div>
			<%
				} else {
			%>
			<div class="pair"><%=msg.getMessage()%></div>
			<%
				}
					}
				}
			%>
		</div>
		<div>
			<input name="<%=self.getUserId()+":"+list.get(0).getMessageId()+":"%>" type="text" style="width: 300px;"
				id="messageInput" maxlength="200" />
		</div>
	</div>
</body>
</html>