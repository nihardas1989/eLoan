<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Loan processing</title>
</head>
<body>
	<!-- write the code to read application number, and send it to admincontrollers
	     callemi method to calculate the emi and other details also provide links
	     to logout and admin home page
	-->
<jsp:include page="header.jsp"/>
<hr/>
<div align="right"><a href="adminhome1.jsp">Admin home</a></div>
<div align="right"><a href="index.jsp">Logout</a></div>

<hr/>
	<div align=center>
		<h2>Loan Processor</h2>
		<form action="admin?action=callemi" method="post">
			<div>
				<div><label for="applno">Enter application number</label> </div>
				<div><input type="text" id="applno" name="applno"> </div>
			</div>
			<div>
				<div><input type="submit" value="Start"> </div>
			</div>
		</form>
	</div>
<hr/>
<jsp:include page="footer.jsp"/>
</body>
</html>