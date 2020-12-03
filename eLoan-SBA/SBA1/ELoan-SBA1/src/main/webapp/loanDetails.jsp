<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Loan tracking</title>
</head>
<body>
	<!-- write the code to display the loan status information 
	     received from usercontrollers' displaystatus method
	-->
<jsp:include page="header.jsp"/>
	<div align="right"><a href="userhome1.jsp">User home</a></div>
	<div align="right"><a href="index.jsp">Logout</a></div>
	
	<hr/>
	<div align=center>
		<h2>Status : <%= request.getAttribute("status") %></h2>
	</div>
	<hr/>
<jsp:include page="footer.jsp"/>
</body>
</html>