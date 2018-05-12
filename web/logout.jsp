<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>Logging out...</title>
  <body>
    <em>Setting session attributes to null.</em>
    <% if (u.isLoggedIn())
      session.invalidate(); %>
    <% response.sendRedirect("index.jsp"); %>
  </body>
</html>
