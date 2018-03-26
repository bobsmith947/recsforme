<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <ul class="nav justify-content-center">
          <li class="nav-item">
            <a class="nav-link" href="login.jsp">Log in</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="signup.jsp">Sign up</a>
          </li>
        </ul>
        <h2>Welcome to your temporary page.</h2>
        <p>This page is unique to your browser, and is automatically populated with any groups added to your likes or dislikes. Please note that entries are stored in <code>localStorage</code>. If you sign up with an account, your lists will be saved in the cloud, able to be accessed anytime, anywhere.<p>
        <div id="list">
          <h3>Your likes:</h3>
          <div class="list-group text-center my-2" id="likes"></div>
          <h3>Your dislikes:</h3>
          <div class="list-group text-center my-2" id="dislikes"></div>
          <h6 id="resetprompt" class="mt-4 mb-3">If you want to clear <code>localStorage</code>, you can use the below button to do so.</h6>
          <button class="btn btn-danger btn-block" type="reset" id="listreset">Reset your list</button>
        </div>
      </c:if>
      <c:if test="${u.loggedIn}">
        <ul class="nav justify-content-center">
          <li class="nav-item">
            <a class="nav-link" href="logout.jsp">Log out</a>
          </li>
        </ul>
        <h2>Welcome to your page, <jsp:getProperty name="u" property="name" />.</h2>
        <p>This page contains your cloud lists. If you have previously saved items locally, you will have to re-add them in order to be save them in the cloud. <code>localStorage</code> will be cleared upon logout, so be sure to be sure to add anything missing before logging out. The contents of <code>localStorage</code> can be viewed in your browser's developer console <kbd>F12</kbd>.</p>
      </c:if>
    </main>
  </body>
</html>
