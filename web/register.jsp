<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption"%>
<!DOCTYPE html>
<html>
  <title>Registration Page</title>
  <body>
    <c:set var="method" value="${pageContext.request.method}" />
    <c:if test="${method == 'GET'}">
      <sql:query var="matches" dataSource="jdbc/MediaRecom">
        SELECT * FROM users
        WHERE username = ?
        <sql:param value="${param.name}" />
      </sql:query>
      <c:if test="${matches.getRowCount() == 1}">
        <h6 class="text-warning res">The username you entered is already taken.</h6>
      </c:if>
      <c:if test="${matches.getRowCount() == 0}">
        <h6 class="text-success res" id="valid-name">The username you entered is available.</h6>
      </c:if>
    </c:if>
    <c:if test="${method == 'POST'}">
      <% CredentialEncryption cred = new CredentialEncryption(request.getParameter("pass")); %>
      <c:choose>
        <c:when test="${param.action == 'reset'}">
          <sql:update var="updated" dataSource="jdbc/MediaRecom">
            UPDATE users
            SET password_hash = '<%= cred.getHash() %>', 
            password_salt = '<%= cred.getSalt() %>'
            WHERE username = ?
            AND email = ?
            <sql:param value="${param.name}" />
            <sql:param value="${param.email}" />
          </sql:update>
          <c:if test="${updated == 1}">
            <h6 class="text-success res">Your password has successfully been reset. You can now log in with the new password.</h6>
          </c:if>
          <c:if test="${updated == 0}">
            <% response.sendError(403); %>
          </c:if>
        </c:when>
        <c:otherwise>
          <c:catch var="ex">
            <sql:update dataSource="jdbc/MediaRecom">
              INSERT INTO users (username, password_hash, password_salt, email, sex, date_of_birth)
              VALUES (?, '<%= cred.getHash() %>', '<%= cred.getSalt() %>', ?, ?, ?)
            <sql:param value="${param.name}" />
            <sql:param value="${param.sex}" />
            <sql:param value="${param.dob}" />
            <sql:param value="${param.email}" />
            </sql:update>
          </c:catch>
          <c:if test="${ex == null}">
            <h5 class="text-success res">Successfully registered!</h5>
            <h6 class="text-success res">You can now log in 
              <a href="user.jsp#login">here.</a>
            </h6>
          </c:if>
          <c:if test="${ex != null}">
            <h5 class="text-warning res">Unable to register.</h5>
          </c:if>
        </c:otherwise>
      </c:choose>
    </c:if>
  </body>
</html>
