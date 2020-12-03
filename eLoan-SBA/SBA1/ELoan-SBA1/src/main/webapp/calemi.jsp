<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EMI calculator</title>
</head>
<body>
 <!--
     Read the values from the admin servlet and cal emi and other details and send to
     to the same admin servlet to update the values in the database 
     
     Shall be used to display loan info and other details has
to be entered by manager
  --> 
<jsp:include page="header.jsp"/> 
<hr/>
	<div align=center>
		<h2>EMI Calculator</h2>
		<form action=admin?action=callemi method="post">
			<div>
				<div><label for="applno">Application number</label> </div>
				<div><input type="text" id="applno" name="applno" value="<%= request.getAttribute("applno") %>" readonly> </div>
			</div>
			<div>
				<div><label for="amotsanctioned">Sanctioned Amount</label> </div>
				<div><input type="text" id="amotsanctioned" name="amotsanctioned"> </div>
			</div>
			<div>
				<div><label for="loanterm">Loan tenure(months)</label> </div>
				<div><input type="text" id="loanterm" name="loanterm"> </div>
			</div>
			<div>
				<div><label for="psd">Payment start</label> </div>
				<div><input type="date" id="psd" name="psd"> </div>
			</div>
			<div>
				<div><label for="interest">Interest rate</label> </div>
				<div><input type="text" id="interest" name="interest" value="10"> </div>
			</div>
			<div>
				<div><input type="submit" value="Done" onclick="success()"> </div>
			</div>
		</form>
	</div>
<hr/>
<jsp:include page="footer.jsp"/>
</body>
</html>