<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="r" scope="request" class="me.recsfor.rec.RecBean" />
<jsp:setProperty name="r" property="id" />
<jsp:setProperty name="r" property="name" />
<jsp:setProperty name="r" property="like" value='<%= Boolean.parseBoolean(request.getParameter("like")) %>' />
<!DOCTYPE html>
<html>
  <head>
    <meta name="author" content="Lucas Kitaev">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="post.css" rel="stylesheet" type="text/css">
    <script src="bundle.js" type="text/javascript" charset="UTF-8" async></script>
    <title>recsforme :: Vote</title>
  </head>
  <body>
    <h2>Your vote has been received!</h2>
  </body>
</html>
