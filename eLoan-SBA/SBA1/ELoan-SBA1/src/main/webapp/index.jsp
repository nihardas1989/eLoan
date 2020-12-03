<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>eLoan</title>
</head>
<body>
	<!-- write the html code to read user credentials and send it to validateservlet
	    to validate and user servlet's registernewuser method if create new user
	    account is selected  
	-->
<%
request.getSession().setAttribute("username","");
request.getSession().setAttribute("password","");

%>	
	<jsp:include page="header.jsp"/>
	<hr/>
	<div align=center>
		<h2>eLoan Login</h2>
		<form action="user?action=validate" method="post">
			<div>
				<div><label for="username">Username</label> </div>
				<div><input type="text" id="username" name="username"> </div>
			</div>
			<div>
				<div><label for="password">Password</label> </div>
				<div><input type="password" id="password" name="password"> </div>
			</div>
			<div>
				<div><input type="submit" value="Login"> </div>
			</div>
			<div>
				<div><a href="user?action=register">New User register</a></div>
			</div>
		</form>
	</div>
	<hr/>
	<jsp:include page="footer.jsp"/>
	
</body>
</html>