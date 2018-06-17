<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption, java.time.LocalDate"%>
<!DOCTYPE html>
<html>
  <title>Registration Page</title>
  <body>
    <c:if test='${pageContext.request.getMethod() == "GET"}'>
      <sql:query var="matches" dataSource="jdbc/MediaRecom">
        SELECT * FROM users
        WHERE uname = ?
        <sql:param value='${pageContext.request.getParameter("name")}' />
      </sql:query>
      <c:choose>
        <c:when test="${matches.getRowCount() == 1}">
          <h6 class="text-warning">The username you entered is already taken.</h6>
        </c:when>
        <c:when test="${matches.getRowCount() == 0}">
          <h6 class="text-success" id="valid-name">The username you entered is available.</h6>
        </c:when>
        <c:otherwise>
          <h6 class="text-danger">Something went wrong. If the issue persists, please contact the administrator.</h6>
        </c:otherwise>
      </c:choose>
    </c:if>
    <c:if test='${pageContext.request.getMethod() == "POST"}'>
      <% CredentialEncryption cred; %>
      <c:choose>
        <c:when test='${pageContext.request.getParameter("action") == "reset"}'>
          <% cred = new CredentialEncryption(request.getParameter("pass")); %>
          <sql:update var="updated" dataSource="jdbc/MediaRecom">
            UPDATE users
            SET pw = '<%= cred.getHash() %>', 
              salt = '<%= cred.getSalt() %>'
            WHERE uname = ?
            AND email = ?
            <sql:param value='${pageContext.request.getParameter("name")}' />
            <sql:param value='${pageContext.request.getParameter("email")}' />
          </sql:update>
          <c:choose>
            <c:when test="${updated == 1}">
              <h6 class="text-success">Your password has successfully been reset. You can now log in with the new password.</h6>
            </c:when>
            <c:when test="${updated == 0}">
              <h6 class="text-danger">Your email and/or username are not correct.</h6>
              <% response.sendError(400); %>
            </c:when>
            <c:otherwise>
              <h6 class="text-danger">Something went wrong. If the issue persists, please contact the administrator.</h6>
              <% response.sendError(500); %>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:catch var="ex">
            <% cred = new CredentialEncryption(request.getParameter("pw")); %>
            <sql:update dataSource="jdbc/MediaRecom">
              INSERT INTO users (uname, pw, joined, sex, dob, email, salt)
              VALUES (?, '<%= cred.getHash() %>', '<%= LocalDate.now() %>', ?, ?, ?, '<%= cred.getSalt() %>')
            <sql:param value='${pageContext.request.getParameter("uname")}' />
            <sql:param value='${pageContext.request.getParameter("sex")}' />
            <sql:param value='${pageContext.request.getParameter("dob")}' />
            <sql:param value='${pageContext.request.getParameter("email")}' />
            </sql:update>
            <sql:query var="newUser" dataSource="jdbc/MediaRecom">
              SELECT id FROM users
              WHERE uname = ?
              <sql:param value='${pageContext.request.getParameter("uname")}' />
            </sql:query>
            <sql:update dataSource="jdbc/MediaRecom">
              INSERT INTO user_likes (uid)
              VALUES (${newUser.getRowsByIndex()[0][0]})
            </sql:update>
            <sql:update dataSource="jdbc/MediaRecom">
              INSERT INTO user_dislikes (uid)
              VALUES (${newUser.getRowsByIndex()[0][0]})
            </sql:update>
          </c:catch>
          <c:if test="${ex == null}">
            <h5 class="text-success">Data successfully registered in the database!</h5>
            <h6 class="text-success">You can now log in on the <a href="user.jsp">user page</a>.</h6>
          </c:if>
          <c:if test="${ex != null}">
            ${pageContext.exception.printStackTrace()}
            <h5 class="text-warning">Unable to register. Please ensure all form fields are valid.</h5>
          </c:if>
        </c:otherwise>
      </c:choose>
    </c:if>
  </body>
</html>
