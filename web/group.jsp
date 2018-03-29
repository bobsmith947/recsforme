<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>recsforme :: Group</title>
  <body>
    <noscript class="alert alert-danger d-block">Scripts have been disabled. Some features may not work.</noscript>
    <main>
      <h1><%= request.getParameter("name")%></h1>
      <button id="removegroup" class="btn btn-danger btn-lg btn-block my-4" data-name='<%= request.getParameter("name")%>' type="reset">
        Remove from list
      </button>
    </main>
    <%-- TODO implement urlencoded -> JSON for vote data to add to database --%>
  </body>
</html>
