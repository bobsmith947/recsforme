<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption, java.time.LocalDate"%>
<!DOCTYPE html>
<html>
  <title>Registration Page</title>
  <body>
    <c:if test='${pageContext.request.getMethod() == "GET"}'>
      <sql:query var="matches" scope="request" dataSource="jdbc/MediaRecom">
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
      <c:catch var="ex">
        <% CredentialEncryption cred = new CredentialEncryption(request.getParameter("pw")); %>
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
        <h6 class="text-success">You can now <a href="login.jsp">log in</a>.</h6>
      </c:if>
      <c:if test="${ex != null}">
        <%-- TODO log exception message --%>
        <% this.log("An error occurred."); %>
        <h5 class="text-warning">Unable to register. Please ensure all form fields are valid.</h5>
      </c:if>
    </c:if>
  </body>
</html>
