<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListGroup, me.recsfor.engine.recommend.Generator, java.util.UUID"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:useBean id="r" scope="session" class="me.recsfor.engine.recommend.RecommendationBean" />
<!DOCTYPE html>
<html>
	<title>Group Page</title>
	<body>
		<c:set var="action" value="${param.action}" />
		<c:set var="status" value="${param.status}" />
		<% ListGroup group; %>
		<c:if test="${action == 'add'}">
			<% group = new ListGroup(request.getParameter("name"),
							request.getParameter("id"), request.getParameter("type")); %>
			<c:if test="${u.loggedIn}">
				<sql:update dataSource="jdbc/MediaRecom">
					INSERT INTO user_groups (user_id, group_gid, liked)
					VALUES (${u.id}, ?, ${status == 'like'})
					<sql:param value="${UUID.fromString(param.id)}" />
				</sql:update>
			</c:if>
			<c:if test="${status == 'like'}">
				<% u.getLikeData().getList().add(group); %>
			</c:if>
			<c:if test="${status == 'dislike'}">
				<% u.getDislikeData().getList().add(group); %>
			</c:if>
		</c:if>
		<c:if test="${action == 'reset'}">
			<c:set var="list" value="${param.list}" />
			<c:if test="${list == 'both'}">
				<c:if test="${u.loggedIn}">
					<sql:update dataSource="jdbc/MediaRecom">
						DELETE FROM user_groups
						WHERE user_id = ${u.id}
					</sql:update>
				</c:if>
				${u.likeData.list.clear()}
				${u.dislikeData.list.clear()}
			</c:if>
			<c:if test="${list == 'like'}">
				<c:if test="${u.loggedIn}">
					<sql:update dataSource="jdbc/MediaRecom">
						DELETE FROM user_groups
						WHERE user_id = ${u.id}
						AND liked = TRUE
					</sql:update>
				</c:if>
				${u.likeData.list.clear()}
			</c:if>
			<c:if test="${list == 'dislike'}">
				<c:if test="${u.loggedIn}">
					<sql:update dataSource="jdbc/MediaRecom">
						DELETE FROM user_groups
						WHERE user_id = ${u.id}
						AND liked = FALSE
					</sql:update>
				</c:if>
				${u.dislikeData.list.clear()}
			</c:if>
		</c:if>
		<c:if test="${action == 'remove'}">
			<% group = new ListGroup(request.getParameter("name"),
							request.getParameter("id"), request.getParameter("type")); %>
			<c:if test="${u.loggedIn}">
				<sql:update dataSource="jdbc/MediaRecom">
					DELETE FROM user_groups
					WHERE user_id = ${u.id}
					AND group_gid = ?
					<sql:param value="${UUID.fromString(param.id)}" />
				</sql:update>
			</c:if>
			<c:if test="${status == 'like'}">
				<% u.getLikeData().getList().remove(group); %>
			</c:if>
			<c:if test="${status == 'dislike'}">
				<% u.getDislikeData().getList().remove(group); %>
			</c:if>
		</c:if>
		<c:if test="${action == 'check'}">
			<% group = new ListGroup(request.getParameter("name"),
						request.getParameter("id"), request.getParameter("type"));
				if (u.getLikeData().getList().contains(group)) {
					response.addHeader("Item-Contained", "like");
				} else if (u.getDislikeData().getList().contains(group)) {
						response.addHeader("Item-Contained", "dislike");
					}%>
		</c:if>
		<c:if test="${action == 'recommend'}">
			<c:if test="${!param.type}">
				<c:if test="${r.recommendations == null}">
					<c:if test="${r.users == null}">
						<sql:query var="users" dataSource="jdbc/MediaRecom">
							SELECT id, username, gid, name, type, liked FROM users, groups, user_groups
							WHERE id != ${u.id}
							AND user_id = id
							AND group_gid = gid
						</sql:query>
						${r.addUsers(users)}
					</c:if>
					<c:set var="gen" value="${Generator(r.users, u.likeData, u.dislikeData)}" />
					<jsp:setProperty name="r" property="recommendations" value="${gen.listRecommendations(0)}" />
				</c:if>
				<div class="list-group text-center res" id="recs">
					<c:forEach var="rec" items="${r.recommendations.list}">
						<a href="${rec.type.context}${rec.id}" class="list-group-item list-group-item-action">${rec.name}</a>
					</c:forEach>
				</div>
			</c:if>
		</c:if>
		<c:if test="${param.type == 'clear'}">
			<jsp:setProperty name="r" property="recommendations" value="${null}" />
		</c:if>
	</body>
</html>
