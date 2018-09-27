<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.engine.recommend.Generator, me.recsfor.app.ListData"%>
<jsp:useBean id="r" scope="session" class="me.recsfor.engine.recommend.RecommendationBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Home</title>
  <body>
    <main>
      <h3>Welcome to recsforme, a media recommendation web app.</h3>
      <p>recsforme is an open source project aimed at creating a fast and simple application for providing media recommendations. The source code is available under the <a href="https://github.com/bobsmith947/recsforme/blob/master/LICENSE">Apache License 2.0</a>. If you have a bug report, feature request, or anything else of that nature, be sure to give the <a href="https://github.com/bobsmith947/recsforme/blob/master/CONTRIBUTING.md">contributing guidelines</a> a read and submit an issue with any appropriate labels. More information on the project itself can be found <a href="https://bobsmith947.github.io">here</a>.</p>
      <h4>Random picks:</h4>
      <c:if test="${rand == null}">
        <c:if test="${r.users == null}">
          <sql:query var="users" dataSource="jdbc/MediaRecom">
            SELECT id, uname, sex, dob FROM users
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
        </c:if>
        <c:set var="gen" value="${Generator(r.users)}" />
        <c:set var="rand" value="${gen.listRandom(0).list}" scope="session" />
      </c:if>
      <div class="list-group text-center mt-3">
        <c:forEach var="group" items="${rand}">
          <a href="${ListData.generateContext(group.type)}${group.id}" class="list-group-item list-group-item-action">${group.name}</a>
        </c:forEach>
      </div>
    </main>
  </body>
</html>
