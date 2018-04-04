<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption"%>
<!DOCTYPE html>
<html>
  <title>Registration Page</title>
  <body>
    <c:if test='${pageContext.request.getMethod() == "GET"}'>
      <sql:query var="matches" scope="request" dataSource="jdbc/MediaRecom">
        SELECT * FROM users
        WHERE uname='<%= request.getParameter("name") %>'
      </sql:query>
      <c:choose>
        <c:when test="${matches.getRowCount() == 1}">
          <div class="text-warning">The username you entered is already taken.</div>
        </c:when>
        <c:when test="${matches.getRowCount() == 0}">
          <div class="text-success">The username you entered is available.</div>
        </c:when>
        <c:otherwise>
          <div class="text-danger">Something went wrong. If the issue persists, please contact the administrator.</div>
        </c:otherwise>
      </c:choose>
    </c:if>
    <c:if test='${pageContext.request.getMethod() == "POST"}'>
      <c:catch var="ex">
        <% CredentialEncryption cred = new CredentialEncryption(request.getParameter("pw")); %>
        <sql:update var="newUser" dataSource="jdbc/MediaRecom">
          INSERT INTO users (uname, pw, joined, sex, dob, email, salt)
          VALUES ('<%= request.getParameter("uname") %>', 
                  '<%= cred.getHash() %>', 
                  '<%= java.time.LocalDate.now() %>', 
                  '<%= request.getParameter("sex") %>', 
                  '<%= request.getParameter("dob") %>', 
                  '<%= request.getParameter("email") %>',
                  '<%= cred.getSalt() %>')
        </sql:update>
        <sql:update dataSource="jdbc/MediaRecom">
          INSERT INTO users_likes (uid)
          VALUES (${newUser.getRowsByIndex()[0][0]})
        </sql:update>
        <sql:update dataSource="jdbc/MediaRecom">
          INSERT INTO users_dislikes (uid)
          VALUES (${newUser.getRowsByIndex()[0][0]})
        </sql:update>
      </c:catch>
      <c:if test="${ex == null}">
        <h5 class="text-success">Data successfully registered in the database!</h5>
        <h6 class="text-success">You can now <a href="login.jsp">log in</a>.</h6>
      </c:if>
      <c:if test="${ex != null}">
        <h5 class="text-warning">Unable to register. Please ensure all form fields are valid.</h5>
      </c:if>
    </c:if>
  </body>
</html>
