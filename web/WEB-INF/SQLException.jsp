<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
	<title>recsforme :: Database Error</title>
	<body>
		<main>
			<h2>SQLException</h2>
			<h3>Database Error</h3>
			<h4>Something went wrong with the database code.</h4>
			<p>The server supplied this error message:</p>
			<code><%= exception.getMessage() %></code>
			<p>If the requested ID was not found, this is most likely due to the current data set being incomplete. If some other error occured, submit a bug report <a href="https://github.com/bobsmith947/recsforme/issues">here</a>, or if you're an admin, check the server logs for more information.</p>
			<h6><a href="#" id="escape">Better go look for something else.</a></h6>
		</main>
	</body>
</html>