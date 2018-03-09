<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: User</title>
  <body>
    <h2>Welcome to your user page!</h2>
    <p>This page is unique to your browser, and is automatically populated with any groups added to your likes or dislikes. Please note that entries are stored in <span style="font-family:Consolas,Courier,monospace">localStorage</span>, so if you clear that, everything will be lost! Naturally, this is only a temporary solution. In the future, there will be full user functionality tied to a remote database.</p>
    <div>
      <h3>Your likes:</h3>
      <ul id="likes"></ul>
      <h3>Your dislikes:</h3>
      <ul id="dislikes"></ul>
    </div>
  </body>
</html>
