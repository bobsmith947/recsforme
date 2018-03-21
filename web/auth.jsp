<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:setProperty name="u" property="name" value='<%= request.getParameter("j_username") %>' />
<jsp:setProperty name="u" property="pass" value='<%= request.getParameter("j_password") %>' />
<!DOCTYPE html>
<html>
  <title>Logging in...</title>
  <body>
    Logging in...
    <sql:query var="exists" scope="request" dataSource="jdbc/MediaRecom">
      SELECT * FROM users
      WHERE uname='<jsp:getProperty name="u" property="name" />'
      AND pw='<jsp:getProperty name="u" property="pass" />'
    </sql:query>
    <c:choose>
      <c:when test="${exists.getRowCount() == 1}">
        <c:redirect url="user.jsp"></c:redirect>
      </c:when>
      <c:when test="${exists.getRowCount() == 0}">
        <c:redirect url="signup.jsp"></c:redirect>
      </c:when>
      <c:otherwise>
        <c:redirect url="login.jsp"></c:redirect>
      </c:otherwise>
    </c:choose>
  </body>
</html>
