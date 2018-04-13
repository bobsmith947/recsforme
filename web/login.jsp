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
      <c:if test="${u.tries < 6}" scope="session" var="notLocked">
        <form data-bind="visible:!resetForm()" action="auth.jsp" method="POST">
          <div class="form-group">
            <label for="uname">Username</label>
            <input data-bind="textInput:name" type="text" class="form-control" id="uname" name="uname" maxlength="18" pattern="\w+" autofocus required>
          </div>
          <div class="form-group">
            <label for="pw">Password</label>
            <input type="password" class="form-control" id="pw" name="pw" required>
            <small class="form-text text-muted">
              Forgot your password?
              <span data-bind="visible:name()===''">First, enter your username to be able request a password reset.</span>
              <a data-bind="visible:name()!=='',click:resetForm" href="#">Reset it.</a>
            </small>
          </div>
          <button type="submit" class="btn btn-primary btn-lg btn-block">Log In</button>
        </form>
        <div class="alert alert-warning">
          You have <strong><c:out value="${6-u.tries}" /> tries</strong> left before you will be locked out.
        </div>
        <form data-bind="visible:resetForm(),submit:requestReset" id="reset-form" method="POST">
          <div class="form-group">
            <label for="email">Email Address</label>
            <input data-bind="textInput:email" type="email" class="form-control" id="email" name="email" maxlength="254" pattern="[@.]\w+" required>
            <small class="form-text text-muted">
              Enter the email address that you signed up for the account named <strong data-bind="text:name()"></strong> with.
            </small>
            <small class="form-text text-muted">If you did not sign up with an email, you cannot reset your password.</small>
          </div>
          <div data-bind="visible:email()!==''" class="form-group">
            <label for="npw">New Password</label>
            <input data-bind="textInput:pass" type="password" class="form-control" id="npw" name="pass" minlength="8" required>
            <small class="form-text text-muted">Enter a secure password you don't use elsewhere.</small>
            <label for="cpw">Confirm Password</label>
            <input data-bind="enable:pass().length>=8,textInput:passCheck" type="password" class="form-control" id="cpw" required>
          </div>
          <button data-bind="enable:pass()===passCheck()&&pass()!==''" type="submit" class="btn btn-warning btn-lg btn-block">
            Reset Password
          </button>
        </form>
        <div id="subres"></div>
      </c:if>
      <c:if test="${!notLocked}">
        <div class="alert alert-danger">
          You have been locked out from being able to log in for this session. Please wait a minimum of <strong>30 minutes</strong> before trying again.
        </div>
        <div class="alert alert-info">
          If you think this has been an error, consider <a class="alert-link" href="https://github.com/bobsmith947/recsforme/issues"> opening an issue on GitHub</a>.
        </div>
      </c:if>
    </main>
  </body>
</html>
