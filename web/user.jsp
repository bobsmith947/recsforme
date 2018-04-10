<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="u" scope="session" class="me.recsfor.app.UserBean" />
<!DOCTYPE html>
<html>
  <title>recsforme :: User</title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <c:if test="${u.message != null && !u.message.isEmpty()}">
      <div class="alert alert-success alert-dismissable fade show text-center">
        <jsp:getProperty name="u" property="message" />
        <button type="button" class="close" data-dismiss="alert">&times;</button>
      </div>
      <jsp:setProperty name="u" property="message" value="" />
    </c:if>
    <main>
      <c:if test="${!u.loggedIn}">
        <ul class="nav justify-content-lg-start justify-content-center mb-4">
          <li class="nav-item">
            <a class="btn btn-secondary btn-sm nav-link mr-1" href="login.jsp">Log in</a>
          </li>
          <li class="nav-item">
            <a class="btn btn-secondary btn-sm nav-link ml-1" href="signup.jsp">Sign up</a>
          </li>
        </ul>
        <h2>Welcome to your temporary page.</h2>
        <p>This page is unique to your browser. If you sign up with an account, your lists will be saved in the cloud, able to be accessed anytime, anywhere. Recommendation generation is not yet available. Keep checking back for development updates.<p>
      </c:if>
      <c:if test="${u.loggedIn}">
        <ul class="nav justify-content-lg-start justify-content-center mb-4">
          <li class="nav-item">
            <a class="btn btn-secondary btn-sm nav-link" href="logout.jsp">Log out</a>
          </li>
        </ul>
        <h2>Welcome to your page, <jsp:getProperty name="u" property="name" />.</h2>
        <p>This page contains your cloud lists. Items that were saved prior to logging in were automatically synced with the cloud. Recommendation generation is not yet available. Keep checking back for development updates.</p>
      </c:if>
      <button class="btn btn-primary btn-block btn-lg my-4" type="button" disabled>
        Generate my recommendations
      </button>
      <div id="list">
        <h3>Your likes:</h3>
        <div class="list-group text-center my-2" id="likes"></div>
        <h3>Your dislikes:</h3>
        <div class="list-group text-center my-2" id="dislikes"></div>
      </div>
      <div class="container">
        <h6 id="resetprompt" class="mt-4 mb-3">If you want to clear your lists, you can use the below buttons to do so.</h6>
        <div class="row">
          <button class="btn btn-danger col-md my-3 mr-md-2 listreset" type="reset" data-list="like">
            Clear your <strong>likes</strong> list
          </button>
          <button class="btn btn-danger col-md my-3 listreset" type="reset" data-list="both">
            Clear <strong>both</strong> of your lists
          </button>
          <button class="btn btn-danger col-md my-3 ml-md-2 listreset" type="reset" data-list="dislike">
            Clear your <strong>dislikes</strong> list
          </button>
        </div>
      </div>
    </main>
  </body>
</html>
