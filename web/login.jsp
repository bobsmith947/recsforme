<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: Log In</title>
  <body>
    <noscript class="alert alert-danger">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <!--<form action="j_security_check" method="POST">-->
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
