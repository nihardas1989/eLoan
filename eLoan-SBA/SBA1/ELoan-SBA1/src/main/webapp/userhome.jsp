<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h4>Dashboard</h4>
<div align="right"><a href="index.jsp">Logout</a></div>
<a href="user?action=application1">Apply Loan</a><br>
<a href="trackloan.jsp">Track Application</a><br>
<a href="editloan.jsp">Edit Application</a>
<jsp:include page="footer.jsp"/>
</body>
</html>