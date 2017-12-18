package models;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Allotment extends HttpServlet{
	HttpServletRequest req;
	HttpServletResponse resp;
	ProfileEntity self;

	public Allotment(HttpServletRequest req, HttpServletResponse resp){
		this.req = req;
		this.resp = resp;
		self = (ProfileEntity)req.getSession().getAttribute("self");
	}

	public void homeAllotment () throws ServletException, IOException  {
		String state = req.getParameter("state");
		if(state==null){state = "home";}
		req.getSession().setAttribute("state",state);
		switch(state){
		case "home":
			System.out.println("home");
			home();
			break;
		case "judge":
			System.out.println("judge");
			judge();
			break;
		case "message":
			System.out.println("message");
			message();
			break;
		case "profile":
			System.out.println("profile");
			home();
			break;
		}
	}

	public void messageAllotment () throws ServletException, IOException {

	}
	void home () throws ServletException, IOException  {
		req.setCharacterEncoding("Windows-31J");
		try {
			TinderDao dao = new TinderDao();
			req.setAttribute("random", dao.getRandomId(self.getUserId(),self.getSex()));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Home_エラー");
		}
	}
	void judge () throws ServletException, IOException  {
		req.setCharacterEncoding("Windows-31J");
		try {
			TinderDao dao = new TinderDao();
			String eva = req.getParameter("eva");
			if(eva!=null && req.getParameter("targetId") != null) {
			int id = Integer.parseInt(req.getParameter("targetId"));
			System.out.println(id);
				if(eva.equals("true")) {
					dao.judge(2, id, true, '男');
				}else if(eva.equals("false")){
					dao.judge(2, id, false, '男');
				}
			}
			req.setAttribute("random", dao.getRandomId(self.getUserId(),self.getSex()));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Judge_エラー");
		}finally {

		}
	}

	void message() throws ServletException,IOException {
		req.setCharacterEncoding("Windows-31J");
		try {
			TinderDao dao = new TinderDao();
			int message_id = Integer.parseInt(req.getParameter("messageId"));
			req.setAttribute("messageData", dao.getMessageData(message_id));
		}catch(Exception e){
			System.out.println("エラー");
		}
	}
}
