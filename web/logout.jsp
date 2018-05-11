<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>Logging out...</title>
  <body>
    <em>Setting session attributes to null.</em>
    <% session.invalidate(); %>
    <% response.sendRedirect("index.jsp"); %>
  </body>
</html>
