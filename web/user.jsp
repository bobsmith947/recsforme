<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: User</title>
  <body>
    <noscript class="alert alert-danger">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <h2>Welcome to your user page!</h2>
      <p>This page is unique to your browser, and is automatically populated with any groups added to your likes or dislikes. Please note that entries are stored in <code>localStorage</code>, so if you clear <code>localStorage</code> accidentally, everything will be lost! Naturally, this is only a temporary solution. In the future, there will be full user functionality tied to a remote database.</p>
      <div id="list">
        <h3>Your likes:</h3>
        <div class="list-group text-center my-2" id="likes"></div>
        <h3>Your dislikes:</h3>
        <div class="list-group text-center my-2" id="dislikes"></div>
      </div>
      <h6 id="resetprompt" class="mt-4 mb-3">If you want to clear <code>localStorage</code>, you can use the below button to do so.</h6>
      <button class="btn btn-danger btn-block" type="reset" id="listreset">Reset your list</button>
    </main>
  </body>
</html>
