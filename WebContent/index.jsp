<%@ page contentType="text/html; charset=Windows-31J"%>
<%@ page import="models.MessageEntity"%>
<%@ page import="models.ProfileEntity"%>
<%@ page import="models.TinderDao"%>

<!DOCTYPE html>
<%
        int id = 2;
        if (request.getParameter("id") != null) {
                id = Integer.parseInt(request.getParameter("id"));
        }
        TinderDao dao = new TinderDao();
        session.setAttribute("self", dao.getProfile(id));
        session.setAttribute("self_id", id);
        response.sendRedirect("home");
        
%>

