<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: Log In</title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <c:if test="${u.message != null && !u.message.isEmpty()}">
      <div class="alert alert-warning alert-dismissable fade show text-center">
        <jsp:getProperty name="u" property="message" />
        <button type="button" class="close" data-dismiss="alert">&times;</button>
      </div>
      <jsp:setProperty name="u" property="message" value="" />
    </c:if>
    <main>
      <div class="alert alert-info">
        Don't have an account? <a class="alert-link" href="signup.jsp">Sign up here.</a>
      </div>
      <form action="auth.jsp" method="POST">
        <div class="form-group">
          <label for="uname">Username</label>
          <input type="text" class="form-control" id="uname" name="uname" maxlength="36" required>
        </div>
        <div class="form-group">
          <label for="pw">Password</label>
          <input type="password" class="form-control" id="pw" name="pw" maxlength="36" required>
        </div>
        <button type="submit" class="btn btn-primary btn-lg btn-block">Log In</button>
      </form>
    </main>
  </body>
</html>
