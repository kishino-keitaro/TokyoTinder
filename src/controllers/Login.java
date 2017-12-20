package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.TinderDao;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF//LoginForm.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String loginId = request.getParameter("loginid");
		String pass = request.getParameter("password");

		HttpSession session = request.getSession();

		try {
			TinderDao dao = new TinderDao();
			int a = dao.login(loginId, pass);
			if (a != -1) {

				session.setAttribute("userId", dao);
				request.getRequestDispatcher("./home.jsp").forward(request,response);
			} else {

				request.setAttribute("error", "IDかパスワードが間違っています");
				request.getRequestDispatcher("/LoginForm.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}