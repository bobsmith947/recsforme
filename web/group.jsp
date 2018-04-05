<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Group</title>
  <body>
    <%-- TODO add ability to remove items --%>
    <c:set var="name" value='${pageContext.request.getParameter("name").replace("\'", "\'\'")}' />
    <c:if test='${pageContext.request.getParameter("like") == "true"}'>
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_likes
        IF LEN(items) > 11
          SET items = LEFT(items, LEN(items)-2) 
          + ',{"name":"${name}","id":"${pageContext.request.getParameter("id")}"}]}'
        ELSE
          SET items = LEFT(items, LEN(items)-2) 
          + '{"name":"${name}","id":"${pageContext.request.getParameter("id")}"}]}'
        WHERE uid = ${u.id}
      </sql:update>
    </c:if>
    <c:if test='${pageContext.request.getParameter("like") == "false"}'>
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_dislikes
        IF LEN(items) > 11
          SET items = LEFT(items, LEN(items)-2) 
          + ',{"name":"${name}","id":"${pageContext.request.getParameter("id")}"}]}'
        ELSE
          SET items = LEFT(items, LEN(items)-2) +
          + '{"name":"${name}","id":"${pageContext.request.getParameter("id")}"}]}'
        WHERE uid = ${u.id}
      </sql:update>
    </c:if>
  </body>
</html>
