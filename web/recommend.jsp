<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.engine.recommend.Generator"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:useBean id="r" scope="session" class="me.recsfor.engine.recommend.RecBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Recommendations</title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <sql:query var="users" dataSource="jdbc/MediaRecom">
        SELECT id, uname, sex, dob FROM users
        WHERE id != ${u.id}
      </sql:query>
      <jsp:setProperty name="r" property="users" value="${Generator.addUsers(users)}" />
      <c:forEach var="user" items="${r.users.keySet()}">
        <sql:query var="likes" dataSource="jdbc/MediaRecom">
          SELECT items FROM user_likes
          WHERE uid = ${user}
        </sql:query>
        <sql:query var="dislikes" dataSource="jdbc/MediaRecom">
          SELECT items FROM user_dislikes
          WHERE uid = ${user}
        </sql:query>
        <c:set var="newUser" value="${Generator.addListsToUser(r.users.get(user), likes, dislikes)}" />
        <c:set var="oldUser" value="${r.users.replace(user, newUser)}" />
      </c:forEach>
      <c:set var="gen" value="${Generator(r.users)}" />
    </main>
  </body>
</html>
