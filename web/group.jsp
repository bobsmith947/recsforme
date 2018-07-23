<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListData, me.recsfor.engine.recommend.Generator"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:useBean id="r" scope="session" class="me.recsfor.engine.recommend.RecommendationBean" />
<!DOCTYPE html>
<html>
  <title>Group Page</title>
  <body>
    <c:set var="action" value="${param.action}" />
    <c:if test="${action != 'recommend'}">
      <c:set var="status" value="${param.status}" />
      <c:set var="item" value="${ListData.generateItem(param.name, param.id, param.type)}" />
      <c:set var="group" value="${ListData.generateGroup(item)}" />
      <c:if test="${action == 'add'}">
        <c:if test="${status == 'like'}">
          <c:choose>
            <c:when test="${u.dislikeData.list.contains(group)}">
              <h5 class="text-danger">This group already exists on your dislikes list.</h5>
              <% response.sendError(400); %>
            </c:when>
            <c:otherwise>
              <c:set var="added" value="${u.likeData.list.add(group)}" />
            </c:otherwise>
          </c:choose>
        </c:if>
        <c:if test="${status == 'dislike'}">
          <c:choose>
            <c:when test="${u.likeData.list.contains(group)}">
              <h5 class="text-danger">This group already exists on your likes list.</h5>
              <% response.sendError(400); %>
            </c:when>
            <c:otherwise>
              <c:set var="added" value="${u.dislikeData.list.add(group)}" />
            </c:otherwise>
          </c:choose>
        </c:if>
        <c:if test="${added}">
          <c:set var="changed" value="${true}" />
          <h5 class="text-success">This group has been added.</h5>
        </c:if>
        <c:if test="${!added}">
          <h5 class="text-warning">This group could not be added.</h5>
        </c:if>
      </c:if>
      <c:if test="${action == 'reset'}">
        <c:set var="changed" value="${true}" />
        <c:set var="list" value="${param.list}" />
        <c:if test="${list == 'both'}">
          <% u.getLikeData().getList().clear(); %>
          <% u.getDislikeData().getList().clear(); %>
          <h5 class="text-success">Both of your lists have been reset.</h5>
        </c:if>
        <c:if test="${list == 'like'}">
          <% u.getLikeData().getList().clear(); %>
          <h5 class="text-success">Your likes list has been reset.</h5>
        </c:if>
        <c:if test="${list == 'dislike'}">
          <% u.getDislikeData().getList().clear(); %>
          <h5 class="text-success">Your dislikes list has been reset.</h5>
        </c:if>
      </c:if>
      <c:if test="${action == 'remove'}">
        <c:if test="${status == 'like'}">
          <c:set var="removed" value="${u.likeData.list.remove(group)}" />
        </c:if>
        <c:if test="${status == 'dislike'}">
          <c:set var="removed" value="${u.dislikeData.list.remove(group)}" />
        </c:if>
        <c:if test="${removed}">
          <c:set var="changed" value="${true}" />
          <h5 class="text-success">This group has been removed.</h5>
        </c:if>
        <c:if test="${!removed}">
          <h5 class="text-warning">This group could not be removed.</h5>
        </c:if>
      </c:if>
      <c:if test="${action == 'check'}">
        <c:choose>
          <c:when test="${u.likeData.list.contains(group)}">
            <h5 class="text-success">This group exists on your likes list.</h5>
            <% response.addHeader("Item-Contained", "like"); %>
          </c:when>
          <c:when test="${u.dislikeData.list.contains(group)}">
            <h5 class="text-success">This group exists on your dislikes list.</h5>
            <% response.addHeader("Item-Contained", "dislike"); %>
          </c:when>
          <c:otherwise>
            <h5 class="text-success">This group does not exist on either of your lists.</h5>
          </c:otherwise>
        </c:choose>
      </c:if>
      <c:if test="${u.loggedIn && changed}">
        <jsp:setProperty name="r" property="recommendations" value="${null}" />
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = ?
          WHERE uid = ${u.id};
          <sql:param value="${ListData.stringifyData(u.likeData)}" />

          UPDATE user_dislikes
          SET items = ?
          WHERE uid = ${u.id};
          <sql:param value="${ListData.stringifyData(u.dislikeData)}" />
        </sql:update>
      </c:if>
    </c:if>
    <c:if test="${action == 'recommend'}">
      <c:if test="${!param.type}">
        <c:if test="${r.recommendations == null}">
          <c:if test="${r.users == null}">
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
          </c:if>
          <c:set var="thisUser" value="${r.users.remove(Integer.valueOf(u.id))}" />
          <c:set var="gen" value="${Generator(r.users, u.likeData, u.dislikeData)}" />
          <jsp:setProperty name="r" property="recommendations" value="${gen.listRecommendations(0)}" />
        </c:if>
        <div class="list-group text-center res" id="recs">
          <c:forEach var="rec" items="${r.recommendations.list}">
            <a href="${ListData.generateContext(rec.type)}${rec.id}" class="list-group-item list-group-item-action">${rec.name}</a>
          </c:forEach>
        </div>
      </c:if>
    </c:if>
    <c:if test="${param.type == 'clear'}">
      <jsp:setProperty name="r" property="recommendations" value="${null}" />
    </c:if>
  </body>
</html>
