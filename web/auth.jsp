<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption, me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:setProperty name="u" property="name" value='${pageContext.request.getParameter("uname")}' />
<!DOCTYPE html>
<html>
  <title>Logging in...</title>
  <body>
    <em>Authenticating the login credentials.</em>
    <sql:query var="matches" scope="request" dataSource="jdbc/MediaRecom">
      SELECT id, pw, salt FROM users
      WHERE uname = ?
      <sql:param value="${u.name}" />
    </sql:query>
    <c:choose>
      <c:when test="${matches.getRowCount() == 1}">
        <c:if test='${CredentialEncryption(pageContext.request.getParameter("pw"), 
                                            matches.getRowsByIndex()[0][2])
                      .validatePassword(matches.getRowsByIndex()[0][1])}' var="correct">
          <jsp:setProperty name="u" property="id" value="${matches.getRowsByIndex()[0][0]}" />
          <jsp:setProperty name="u" property="loggedIn" value="true" />
          <jsp:setProperty name="u" property="message" value="Successfully logged in." />
          <sql:query var="likesList" dataSource="jdbc/MediaRecom">
            SELECT items FROM user_likes
            WHERE uid = ${u.id}
          </sql:query>
          <sql:query var="dislikesList" dataSource="jdbc/MediaRecom">
            SELECT items FROM user_dislikes
            WHERE uid = ${u.id}
          </sql:query>
          <jsp:setProperty name="u" property="likeData" value="${ListData.mapData(likesList
                                                               .getRowsByIndex()[0][0])}" />
          <jsp:setProperty name="u" property="dislikeData" value="${ListData.mapData(dislikesList
                                                                  .getRowsByIndex()[0][0])}" />
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
  </body>
</html>
