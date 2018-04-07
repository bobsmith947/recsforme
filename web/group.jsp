<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Group</title>
  <body>
    <c:if test='${pageContext.request.getParameter("action") == null}'>
      <c:set var="name" value='${pageContext.request.getParameter("name").replace("\'", "\'\'")}' />
      <c:if test='${pageContext.request.getParameter("like") == "true"}'>
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) 
            + ',{"name":"${name}","id":"${pageContext.request.getParameter("id")}"}]}'
          WHERE uid = ${u.id} AND LEN(items) > 11;
          -- for first entry
          UPDATE user_likes
          SET items = LEFT(items, LEN(items)-2) 
            + '{"name":"${name}","id":"${pageContext.request.getParameter("id")}","type":"${pageContext.request.getParameter("type")}}]}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
        </sql:update>
      </c:if>
      <c:if test='${pageContext.request.getParameter("like") == "false"}'>
        <sql:update dataSource="jdbc/MediaRecom">
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) 
            + ',{"name":"${name}","id":"${pageContext.request.getParameter("id")}"}]}'
          WHERE uid = ${u.id} AND LEN(items) > 11
          -- for first entry
          UPDATE user_dislikes
          SET items = LEFT(items, LEN(items)-2) +
            + '{"name":"${name}","id":"${pageContext.request.getParameter("id")}"}]}'
          WHERE uid = ${u.id} AND LEN(items) <= 11;
        </sql:update>
      </c:if>
    </c:if>
    <c:if test='${pageContext.request.getParameter("action") == "reset" && u.loggedIn}'>
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_likes
        SET items = DEFAULT
        WHERE uid = ${u.id}
      </sql:update>
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_dislikes
        SET items = DEFAULT
        WHERE uid = ${u.id}
      </sql:update>
      <jsp:setProperty name="u" property="likeData" value="${null}" />
      <jsp:setProperty name="u" property="dislikeData" value="${null}" />
    </c:if>
    <c:if test='${pageContext.request.getParameter("action") == "remove" && u.loggedIn}'>
      <%-- TODO implement group removal --%>
    </c:if>
    <c:if test='${pageContext.request.getParameter("action") == "check" && u.loggedIn}'>
      <span id="checkres"><% out.println(u.checkList(request.getParameter("name"))); %></span>
    </c:if>
  </body>
</html>
