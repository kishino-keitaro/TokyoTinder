package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Allotment;

public class HomeServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			System.out.println("START");
			Allotment all = new Allotment(req, resp);
			all.homeAllotment();
		} catch (Exception e) {
			System.out.println("homeservlet_ÉGÉâÅ[");
		} finally {
			ServletContext context = getServletContext();
			RequestDispatcher rd = context.getRequestDispatcher("/home.jsp");
			rd.forward(req, resp);
		}
	}
}
