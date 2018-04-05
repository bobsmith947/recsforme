<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption, me.recsfor.app.ListData"%>
<!DOCTYPE html>
<html>
  <title>Logging in...</title>
  <body>
    <c:if test='${pageContext.request.getParameter("type") == null}'>
      <jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
      <jsp:setProperty name="u" property="name" value='<%= request.getParameter("uname") %>' />
      <jsp:setProperty name="u" property="pass" value='<%= request.getParameter("pw") %>' />
      <em>Authenticating your user...</em>
      <sql:query var="matches" scope="request" dataSource="jdbc/MediaRecom">
        SELECT id, pw, salt FROM users
        WHERE uname='<jsp:getProperty name="u" property="name" />'
      </sql:query>
      <c:choose>
        <c:when test="${matches.getRowCount() == 1}">
          <c:if test="${CredentialEncryption.validatePassword(u.pass, matches.getRowsByIndex()[0][1], matches.getRowsByIndex()[0][2])}" var="correct">
            <jsp:setProperty name="u" property="id" value="${matches.getRowsByIndex()[0][0]}" />
            <sql:query var="likesList" dataSource="jdbc/MediaRecom">
              SELECT items FROM user_likes
              WHERE uid = ${u.id}
            </sql:query>
            <sql:query var="dislikesList" dataSource="jdbc/MediaRecom">
              SELECT items FROM user_dislikes
              WHERE uid = ${u.id}
            </sql:query>
            <c:set scope="session" var="data" value='${ListData.createData(likesList.getRowsByIndex()[0][0], dislikesList.getRowsByIndex()[0][0])}' />
<!--            <script type="text/javascript">
              var i;
              var likes = JSON.parse('${likes.replace("'", "\\'")}').list;
              var dislikes = JSON.parse('${dislikes.replace("'", "\\'")}').list;
              for (i = 0; i < likes.length; i++)
                localStorage.setItem(likes[i].name, "like");
              for (i = 0; i < dislikes.length; i++)
                localStorage.setItem(dislikes[i].name, "dislike");
              console.log("localStorage populated.");
              window.open("user.jsp", "_self");
            </script>-->
            <jsp:setProperty name="u" property="loggedIn" value="true" />
            <jsp:setProperty name="u" property="message" value="Successfully logged in." />
            <c:redirect url="user.jsp" />
          </c:if>
          <c:if test="${!correct}">
            <jsp:setProperty name="u" property="message" value="The password you entered is incorrect." />
            <jsp:setProperty name="u" property="tries" value="${u.tries + 1}" />
            <c:redirect url="login.jsp" />
          </c:if>
        </c:when>
        <c:when test="${matches.getRowCount() == 0}">
          <jsp:setProperty name="u" property="message" value="Unable to find a matching username." />
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