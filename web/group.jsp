<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Group</title>
  <body>
    <%-- TODO escape ' characters --%>
    <c:if test='${pageContext.request.getParameter("like") == "true"}'>
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_likes
        SET items += '{"name": "${pageContext.request.getParameter("name")}", "id": "${pageContext.request.getParameter("id")}"}'
        WHERE uid = ${u.id}
      </sql:update>
    </c:if>
    <c:if test='${pageContext.request.getParameter("like") == "false"}'>
      <sql:update dataSource="jdbc/MediaRecom">
        UPDATE user_dislikes
        SET items += '{"name": "${pageContext.request.getParameter("name")}", "id": "${pageContext.request.getParameter("id")}"}'
        WHERE uid = ${u.id}
      </sql:update>
    </c:if>
  </body>
</html>
