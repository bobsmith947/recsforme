<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>Logging out...</title>
  <body>
    <em>Setting user session bean to null...</em>
    <% session.invalidate(); %>
    <script type="text/javascript">
      localStorage.clear();
      console.log("localStorage cleared.");
      window.open("index.jsp", "_self");
    </script>
    <%--<c:redirect url="index.jsp" />--%>
  </body>
</html>
