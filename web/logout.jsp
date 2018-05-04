<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <title>Logging out...</title>
  <body>
    <em>Setting session attributes to null.</em>
    <% session.invalidate(); %>
    <script type="text/javascript">
      localStorage.clear();
      console.log("localStorage cleared.");
      window.open("index.jsp", "_self");
    </script>
  </body>
</html>
