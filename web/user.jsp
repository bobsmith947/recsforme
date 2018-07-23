<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="me.recsfor.app.ListData"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<jsp:useBean id="r" scope="session" class="me.recsfor.engine.recommend.RecommendationBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: User</title>
  <body>
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
        <ul class="nav justify-content-lg-start justify-content-center mb-4">
          <li class="nav-item">
            <a class="btn btn-secondary btn-sm nav-link mr-1 profilelink" href="user.jsp#login" data-toggle="modal" data-target="#login">Log in</a>
          </li>
          <li class="nav-item">
            <a class="btn btn-secondary btn-sm nav-link ml-1 profilelink" href="signup.jsp">Sign up</a>
          </li>
        </ul>
        <div id="login" class="modal fade text-dark" tabindex="-1">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" data-bind="if:!resetForm()">Log in to your account</h5>
                <h5 class="modal-title" data-bind="if:resetForm">Reset your password</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
              </div>
              <div class="modal-body">
                <c:if test="${u.tries < 6}" scope="session" var="notLocked">
                  <div id="subres"></div>
                  <form data-bind="visible:!resetForm()" action="login.jsp" method="POST">
                    <div class="form-group">
                      <label for="name">Username</label>
                      <input data-bind="textInput:name" type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                      <label for="pw">Password</label>
                      <input type="password" class="form-control" id="pw" name="pass" required>
                      <small class="form-text text-muted">
                        Forgot your password?
                        <span data-bind="visible:name()===''">First, enter your username to be able to request a password reset for that account.</span>
                        <a data-bind="visible:name()!=='',click:resetForm" href="#">Reset it.</a>
                      </small>
                    </div>
                    <c:if test="${u.likeData.list.size() > 0 || u.dislikeData.list.size() > 0}">
                      <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="update" name="update" value="true" />
                        <label class="form-check-label" for="update">Update lists to add current items</label>
                        <small class="form-text text-muted">Conflicting items will be removed.</small>
                      </div>
                    </c:if>
                    <button type="submit" class="btn btn-primary btn-lg btn-block my-3">Log In</button>
                  </form>
                  <div class="alert alert-warning">
                    You have 
                    <strong>${6-u.tries} tries</strong>
                    left before you will be locked out.
                  </div>
                  <form data-bind="visible:resetForm,submit:requestReset" id="reset-form" method="POST">
                    <small class="form-text text-muted">
                      Entered the wrong username?
                      <a data-bind="click:cancelReset" href="#">Go back.</a>
                    </small>
                    <div class="form-group">
                      <label for="email">Email Address</label>
                      <input data-bind="textInput:email" type="email" class="form-control" id="email" name="email" required>
                      <small class="form-text text-muted">
                        Enter the email address that you signed up for the account named 
                        <strong data-bind="text:name()"></strong>
                        with.
                      </small>
                      <small class="form-text text-muted">If you do not have an email associated with your account, you cannot reset your password.</small>
                    </div>
                    <div class="form-group">
                      <label for="npw">New Password</label>
                      <input data-bind="enable:beginReset(),textInput:pass" type="password" class="form-control" id="npw" name="pass" minlength="8" required>
                      <small class="form-text text-muted">Enter a password you don't use elsewhere.</small>
                      <small class="form-text text-muted">Your password must be at least 8 characters.</small>
                      <label for="cpw">Confirm Password</label>
                      <input data-bind="enable:pass().length>=8,textInput:check" type="password" class="form-control" id="cpw" required>
                    </div>
                    <button data-bind="enable:pass()===check()&&pass()!==''" type="submit" class="btn btn-warning btn-lg btn-block mt-3" disabled>
                      Reset Password
                    </button>
                  </form>
                </c:if>
                <c:if test="${!notLocked}">
                  <div class="alert alert-danger">
                    You have been locked out from being able to log in for this session. Please wait a minimum of 
                    <strong id="timeout">${pageContext.session.maxInactiveInterval}</strong>
                    before trying again. The timer will be reset if you refresh the page.
                  </div>
                  <div class="alert alert-info">
                    If you think this has been an error, consider <a class="alert-link" href="https://github.com/bobsmith947/recsforme/issues"> opening an issue on GitHub</a>.
                  </div>
                </c:if>
              </div>
            </div>
          </div>
        </div>
      </c:if>
      <h2>Welcome to your temporary page.</h2>
      <p>This page is unique to your browser, and will be cleared if the browsing session expires after 30 minutes of inactivity, or if you close your browser. If you sign up with an account, your lists will be saved, able to be accessed anytime, anywhere.</p>
      <c:if test="${u.loggedIn}">
        <ul class="nav justify-content-lg-start justify-content-center mb-4">
          <li class="nav-item">
            <a class="btn btn-secondary btn-sm nav-link profilelink" href="logout.jsp">Log out</a>
          </li>
        </ul>
        <h2>Welcome to your page, <c:out value="${u.name}" />.</h2>
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
        <c:if test="${r.recommendations == null}">
          <button id="recgen" type="button" class="btn btn-primary btn-block btn-lg my-4">
            Generate my recommendations
          </button>
        </c:if>
      <div id="recs">
        <c:if test="${r.recommendations != null}">
          <div class="list-group text-center res">
            <h3>Your recommendations:</h3>
            <c:forEach var="rec" items="${r.recommendations.list}">
              <a href="${ListData.generateContext(rec.type)}${rec.id}" class="list-group-item list-group-item-action">${rec.name}</a>
            </c:forEach>
          </div>
        </c:if>
      </div>
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
