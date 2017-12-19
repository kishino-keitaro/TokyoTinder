package org.ukiuni.inspect.websocket.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import models.MessageEntity;
import models.ProfileEntity;
import models.TinderDao;

@ServerEndpoint("/loadMessage")
public class WebSocketEndpointAction {

	public static List<Session> sessions = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session) {
		// 開始時
		sessions.add(session);
	}

	@OnMessage
	public void onMessage(String message, Session mysession) throws Exception {
		// クライアントからの受信時
		System.out.println(message);
		String[] temp = message.split(":");
		System.out.println(Arrays.toString(temp));
		TinderDao dao = new TinderDao();
		int userId = 0;
		int messageId = 0;
		switch (temp[0]) {
		case "message":
			int id = Integer.parseInt(temp[1]);
			messageId = Integer.parseInt(temp[2]);
			message = temp[3];

			dao.sendMessage(message, messageId, id);
			for (Session session : sessions) {
				session.getBasicRemote().sendText("{\"user\":\"" + id + "\", \"command\":\"message\", \"text\": \""
						+ message.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
			}
			break;
		case "createMessage":
			// 0:コマンド,1:ユーザーID,2:メッセージID,3:メッセージ相手
			userId  = Integer.parseInt(temp[1]);
			messageId = Integer.parseInt(temp[2]);
			int targetId = Integer.parseInt(temp[3]);
			String text = "";
			for(MessageEntity msg : dao.getMessageData(messageId)){
				if(msg.getSpeaker() == userId) {
					text += "<div class = self>" + msg.getMessage() + "</div>";
				}else if(msg.getSpeaker() == 0){
					text += "<div class = announce>" + msg.getMessage() + "</div>";
				}else {
					text += "<div class = pair>" + msg.getMessage() + "</div>";
				}
			}

			ProfileEntity t_ent = dao.getProfile(targetId);
			mysession.getBasicRemote().sendText("{\"text\":\"" + text + "\",\"command\":\"createMessage\", \"messageId\":\""+ temp[1] + "\", \"UserId\":\""+ temp[2] + "\",\"name\":\""+ t_ent.getName() + "\", \"age\":\""+ t_ent.getAge() + "\", \"image\":\""+ t_ent.getImage() + "\", \"comment\":\""+ t_ent.getComment() + "\"}");
			break;
		case "judge":
			// 0:コマンド,1:評価,2:評価者ID,3:評価者性別,4:対象者ID
			boolean match = dao.judge(Integer.parseInt(temp[2]), Integer.parseInt(temp[4]), temp[1].equals("good"), temp[3].charAt(0));
			ProfileEntity r_ent = dao.getRandomId(Integer.parseInt(temp[2]), temp[3].charAt(0));
			String m_Id = "false";
			System.out.println("judge is :" + match);
			if(match) {
				m_Id = ""+ dao.getMessageId(Integer.parseInt(temp[2]),Integer.parseInt(temp[4]));
			}
			mysession.getBasicRemote().sendText("{\"user\":\""+ r_ent.getUserId() +"\",\"command\":\"judge\", \"name\":\""+ r_ent.getName() + "\", \"age\":\""+ r_ent.getAge() + "\", \"image\":\""+ r_ent.getImage() + "\", \"comment\":\""+ r_ent.getComment() + "\", \"match\":\""+m_Id+"\"}");
			break;
		}
	}

	@OnError
	public void onError(Throwable t) throws IOException {
		// エラー発生時
		for (Session session : sessions) {
			session.getBasicRemote().sendText("{\"command\":\"error\", \"text\": \""
					+ t.getMessage().replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
		}
	}

	@OnClose
	public void onClose(Session session) {
		// 完了時
		System.out.println("!!!!!!!!!!!!!!!!");
		sessions.remove(session);
	}

	String createMessage (ArrayList<MessageEntity> list, int userId) {

		String text = "";
		for(MessageEntity msg : list){
			if(msg.getSpeaker() == userId) {
				text += "<div class = self>" + msg.getMessage() + "</div>";
			}else if(msg.getSpeaker() == 0){
				text += "<div class = announce>" + msg.getMessage() + "</div>";
			}else {
				text += "<div class = pair>" + msg.getMessage() + "</div>";
			}
		}
		return text;
	}
}
