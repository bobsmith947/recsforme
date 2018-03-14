<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: User</title>
  <body>
    <noscript class="alert alert-danger">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <h2>Welcome to your user page!</h2>
      <p>This page is unique to your browser, and is automatically populated with any groups added to your likes or dislikes. Please note that entries are stored in <code>localStorage</code>, so if you clear that, everything will be lost! Naturally, this is only a temporary solution. In the future, there will be full user functionality tied to a remote database.</p>
      <div>
        <h3>Your likes:</h3>
        <ul class="list-group text-center" id="likes"></ul>
        <h3>Your dislikes:</h3>
        <ul class="list-group text-center" id="dislikes"></ul>
      </div>
    </main>
  </body>
</html>
