<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.CredentialEncryption, java.util.UUID"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:useBean id="r" scope="session" class="me.recsfor.engine.recommend.RecommendationBean" />
<jsp:setProperty name="u" property="name" />
<!DOCTYPE html>
<html>
	<title>Logging in...</title>
	<body>
		<c:if test="${pageContext.request.method == 'POST'}">
			<sql:query var="matches" dataSource="jdbc/MediaRecom">
				SELECT id, password_hash, password_salt FROM users
				WHERE username = ?
				<sql:param value="${u.name}" />
			</sql:query>
			<c:choose>
				<c:when test="${matches.getRowCount() == 1}">
					<c:catch var="ex">
						<c:set var="valid" value="${CredentialEncryption(param.pass, matches.getRowsByIndex()[0][2])
													.validatePassword(matches.getRowsByIndex()[0][1])}" />
					</c:catch>
					<c:if test="${ex != null}">
						<c:set scope="session" var="message" value="Your password could not be validated. Please try again." />
						<c:set scope="session" var="status" value="danger" />
					</c:if>
					<c:if test="${valid}">
						<jsp:setProperty name="u" property="id" value="${matches.getRowsByIndex()[0][0]}" />
						<jsp:setProperty name="r" property="recommendations" value="${null}" />
						<jsp:setProperty name="u" property="loggedIn" value="${true}" />
						<sql:query var="groups" dataSource="jdbc/MediaRecom">
							SELECT name, gid, type, liked FROM groups, user_groups
							WHERE user_id = ${u.id} 
							AND group_gid = groups.gid 
							ORDER BY time_added
						</sql:query>
						<c:if test="${param.update}">
							${u.removeGroups(groups)}
							<c:forEach items="${u.likeData.list}" var="group">
								<sql:update dataSource="jdbc/MediaRecom">
									INSERT INTO user_groups (user_id, group_gid, liked)
									VALUES (${u.id}, ?, TRUE)
									<sql:param value="${UUID.fromString(group.id)}" />
								</sql:update>
							</c:forEach>
							<c:forEach items="${u.dislikeData.list}" var="group">
								<sql:update dataSource="jdbc/MediaRecom">
									INSERT INTO user_groups (user_id, group_gid, liked)
									VALUES (${u.id}, ?, FALSE)
									<sql:param value="${UUID.fromString(group.id)}" />
								</sql:update>
							</c:forEach>
						</c:if>
						<c:if test="${!param.update}">
							${u.likeData.list.clear()}
							${u.dislikeData.list.clear()}
						</c:if>
						${u.addGroups(groups)}
						<c:set scope="session" var="message" value="Successfully logged in." />
						<c:set scope="session" var="status" value="success" />
					</c:if>
					<c:if test="${!valid}">
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
		</c:if>
		<c:if test="${param.action == 'check'}">
			<c:if test="${!u.loggedIn}">
				<ul class="nav justify-content-lg-start justify-content-center mb-4">
					<li class="nav-item">
						<a class="btn btn-secondary btn-sm nav-link mr-1 profilelink" href="user.jsp#login" data-toggle="modal" data-target="#login">Log in</a>
					</li>
					<li class="nav-item">
						<a class="btn btn-secondary btn-sm nav-link ml-1 profilelink" href="signup.jsp">Sign up</a>
					</li>
				</ul>
			</c:if>
			<c:if test="${u.loggedIn}">
				<ul class="nav justify-content-lg-start justify-content-center mb-4">
					<li class="nav-item">
						<a class="btn btn-secondary btn-sm nav-link profilelink" href="logout.jsp">Log out</a>
					</li>
				</ul>
			</c:if>
		</c:if>
	</body>
</html>
