package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.TinderDao;

/**
 * Servlet implementation class UserDataServlet
 */
public class Registration extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String birth = request.getParameter("birth");
		String sex = request.getParameter("sex");
		String comment = request.getParameter("comment");
		String image = request.getParameter("image");
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");

		// validate given input
		try {
			TinderDao dao = new TinderDao();
			if (!name.isEmpty() || !sex.isEmpty() || !comment.isEmpty()
					|| !image.isEmpty() || !login.isEmpty() || !pass.isEmpty()) {

				dao.signUp(name, birth, sex, comment, image, login, pass);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
				out.println("<font color=red>未入力の項目があります</font>");
				rd.include(request, response);
			}
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
