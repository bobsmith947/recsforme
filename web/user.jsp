<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: User</title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <c:if test="${message != null}">
      <div class="alert alert-${status} alert-dismissable fade show text-center">
        <c:out value="${message}" />
        <button type="button" class="close" data-dismiss="alert">&times;</button>
      </div>
      <c:set var="message" value="${null}" />
      <c:set var="status" value="${null}" />
    </c:if>
    <main>
      <c:if test="${!u.loggedIn}">
        <c:if test="${u.tries < 6}" scope="session" var="notLocked">
          <ul class="nav justify-content-lg-start justify-content-center mb-4">
            <li class="nav-item">
              <a class="btn btn-secondary btn-sm nav-link mr-1" href="#" data-bind="click:loginForm">Log in</a>
            </li>
            <li class="nav-item">
              <a class="btn btn-secondary btn-sm nav-link ml-1" href="signup.jsp">Sign up</a>
            </li>
          </ul>
          <div id="login" data-bind="visible:loginForm">
            <div>
              <form data-bind="visible:!resetForm()" action="login.jsp" method="POST">
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
              <c:if test="${u.likeData.list.size() > 0 || u.dislikeData.list.size() > 0}">
                <div class="alert alert-danger">Local lists will be cleared upon logging in.</div>
              </c:if>
              <div class="alert alert-warning">
                You have <strong><c:out value="${6-u.tries}" /> tries</strong> left before you will be locked out.
              </div>
            </div>
            <div>
              <form data-bind="visible:resetForm,submit:requestReset" id="reset-form" method="POST">
                <div class="form-group">
                  <label for="email">Email Address</label>
                  <input data-bind="textInput:email" type="email" class="form-control" id="email" name="email" required>
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
            </div>
          </div>
        </c:if>
        <c:if test="${!notLocked}">
          <div class="alert alert-danger">
            You have been locked out from being able to log in for this session. Please wait a minimum of <strong>30 minutes</strong> before trying again.
          </div>
          <div class="alert alert-info">
            If you think this has been an error, consider <a class="alert-link" href="https://github.com/bobsmith947/recsforme/issues"> opening an issue on GitHub</a>.
          </div>
        </c:if>
        <h2>Welcome to your temporary page.</h2>
        <p>This page is unique to your browser, and will be cleared if it expires (after 30 minutes of inactivity), or if you close your browser. If you sign up with an account, your lists will be saved in the cloud, able to be accessed anytime, anywhere. Recommendation generation is not yet available. Keep checking back for development updates.</p>
      </c:if>
      <c:if test="${u.loggedIn}">
        <ul class="nav justify-content-lg-start justify-content-center mb-4">
          <li class="nav-item">
            <a class="btn btn-secondary btn-sm nav-link" href="logout.jsp">Log out</a>
          </li>
        </ul>
        <h2>Welcome to your page, <jsp:getProperty name="u" property="name" />.</h2>
        <p>This page contains your cloud lists. Lists are only synced upon logout, so don't forget to do so when you're done. Recommendation generation is not yet available. Keep checking back for development updates.</p>
      </c:if>
      <div class="text-center" id="list">
        <h3>Your likes:</h3>
        <div class="list-group my-2" id="likes">
          <c:forEach var="lg" items="${u.likeData.list}">
            <a href="${ListData.generateContext(lg.type)}${lg.id}" class="list-group-item list-group-item-action">${lg.name}</a>
          </c:forEach>
        </div>
        <h3>Your dislikes:</h3>
        <div class="list-group my-2" id="dislikes">
          <c:forEach var="dg" items="${u.dislikeData.list}">
            <a href="${ListData.generateContext(dg.type)}${dg.id}" class="list-group-item list-group-item-action">${dg.name}</a>
          </c:forEach>
        </div>
      </div>
      <button class="btn btn-primary btn-block btn-lg my-4" type="button" disabled>
        Generate my recommendations
      </button>
      <div class="container">
        <h6 id="resetprompt" class="mt-4 mb-3">If you want to clear your lists, you can use the below buttons to do so.</h6>
        <div class="row">
          <button class="btn btn-danger col-md my-3 mr-md-2 listreset" type="reset" data-list="like">
            Clear your likes list
          </button>
          <button class="btn btn-danger col-md my-3 listreset" type="reset" data-list="both">
            Clear both of your lists
          </button>
          <button class="btn btn-danger col-md my-3 ml-md-2 listreset" type="reset" data-list="dislike">
            Clear your dislikes list
          </button>
        </div>
      </div>
    </main>
  </body>
</html>
