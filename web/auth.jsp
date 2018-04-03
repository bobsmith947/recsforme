<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>Logging in...</title>
  <body>
    <%-- TODO validate password --%>
    <c:if test='${pageContext.request.getParameter("type") == null}'>
      <jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
      <jsp:setProperty name="u" property="name" value='<%= request.getParameter("uname") %>' />
      <jsp:setProperty name="u" property="pass" value='<%= request.getParameter("pw") %>' />
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
          <jsp:setProperty name="u" property="tries" value="${u.tries + 1}" />
          <c:redirect url="login.jsp" />
        </c:when>
        <c:otherwise>
          <jsp:setProperty name="u" property="message" value="Something has gone wrong. If the issue persists, please contact the administrator." />
          <jsp:setProperty name="u" property="tries" value="${u.tries + 1}" />
          <c:redirect url="login.jsp" />
        </c:otherwise>
      </c:choose>
    </c:if>
    <c:if test='${pageContext.request.getParameter("type") == "reset"}'>
      <sql:update var="updated" dataSource="jdbc/MediaRecom">
        UPDATE users
        SET pw='<c:out value='${pageContext.request.getParameter("pass")}' />'
        WHERE uname='<c:out value='${pageContext.request.getParameter("name")}' />'
        AND email='<c:out value='${pageContext.request.getParameter("email")}' />'
      </sql:update>
      <c:choose>
        <c:when test="${updated == 1}">
          <h6 class="text-success">Your password has successfully been reset. You can now log in with the new password.</h6>
        </c:when>
        <c:when test="${updated == 0}">
          <h6 class="text-warning">Unable to reset your password. Please try again.</h6>
        </c:when>
        <c:otherwise>
          <h6 class="text-danger">Something went wrong. If the issue persists, please contact the administrator.</h6>
        </c:otherwise>
      </c:choose>
    </c:if>
  </body>
</html>
