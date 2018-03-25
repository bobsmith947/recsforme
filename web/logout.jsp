<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>Logging out...</title>
  <body>
    <em>Setting user session bean to null...</em>
    <jsp:setProperty name="u" property="name" value="" />
    <jsp:setProperty name="u" property="pass" value="" />
    <jsp:setProperty name="u" property="loggedIn" value="false" />
    <jsp:setProperty name="u" property="message" value="Successfully logged out." />
    <script type="text/javascript">
      localStorage.clear();
      console.log("localStorage cleared");
    </script>
    <c:redirect url="user.jsp" />
  </body>
</html>
