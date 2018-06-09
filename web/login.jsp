<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption, me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:setProperty name="u" property="name" param="uname" />
<!DOCTYPE html>
<html>
  <title>Logging in...</title>
  <body>
    <em>Authenticating the login credentials.</em>
    <sql:query var="matches" dataSource="jdbc/MediaRecom">
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
          <c:set scope="session" var="message" value="Successfully logged in." />
          <c:set scope="session" var="status" value="success" />
        </c:if>
        <c:if test="${!correct}">
          <c:set scope="session" var="message" value="The password you entered is incorrect." />
          <c:set scope="session" var="status" value="warning" />
          <jsp:setProperty name="u" property="tries" value="${u.tries + 1}" />
        </c:if>
      </c:when>
      <c:when test="${matches.getRowCount() == 0}">
        <c:set scope="session" var="message" value="Unable to find a matching username." />
        <c:set scope="session" var="status" value="warning" />
        <jsp:setProperty name="u" property="tries" value="${u.tries + 1}" />
      </c:when>
      <c:otherwise>
        <c:set scope="session" var="message" value="Something has gone wrong. If the issue persists, please contact the administrator." />
        <c:set scope="session" var="status" value="danger" />
        <jsp:setProperty name="u" property="tries" value="${u.tries + 1}" />
      </c:otherwise>
    </c:choose>
    <c:redirect url="user.jsp" />
  </body>
</html>
