<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:setProperty name="u" property="name" value='<%= request.getParameter("uname") %>' />
<jsp:setProperty name="u" property="pass" value='<%= request.getParameter("pw") %>' />
<!DOCTYPE html>
<html>
  <title>Logging in...</title>
  <body>
    <em>Authenticating your user...</em>
    <sql:query var="matches" scope="request" dataSource="jdbc/MediaRecom">
      SELECT * FROM users
      WHERE uname='<jsp:getProperty name="u" property="name" />'
      AND pw='<jsp:getProperty name="u" property="pass" />'
    </sql:query>
    <c:choose>
      <c:when test="${matches.getRowCount() == 1}">
        <jsp:setProperty name="u" property="loggedIn" value="true" />
        <jsp:setProperty name="u" property="message" value="Successfully logged in." />
        <c:redirect url="user.jsp" />
      </c:when>
      <c:when test="${matches.getRowCount() == 0}">
        <jsp:setProperty name="u" property="message" value="Unable to find a matching username/password." />
        <c:redirect url="login.jsp" />
      </c:when>
      <c:otherwise>
        <jsp:setProperty name="u" property="message" value="Something has gone wrong. If the issue persists, please contact the administrator." />
        <c:redirect url="login.jsp" />
      </c:otherwise>
    </c:choose>
  </body>
</html>
