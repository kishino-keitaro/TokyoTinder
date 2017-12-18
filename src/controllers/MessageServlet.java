package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.TinderDao;

public class MessageServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("Windows-31J");
		try {
			TinderDao dao = new TinderDao();
			req.setAttribute("messageData", dao.getMessageData(1));
		}catch(Exception e){
			System.out.println("ÉGÉâÅ[");
		}finally {
			ServletContext context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/test.jsp");
			rd.forward(req, resp);
		}
	}
}
