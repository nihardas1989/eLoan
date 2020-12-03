<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@page import="com.iiht.evaluation.eloan.model.LoanInfo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Loans</title>
</head>
<body>
	<!-- write code to display all the loan details 
             which are received from the admin controllers' listall method
	--> 
	<jsp:include page="header.jsp"/>
	<div align="right"><a href="adminhome1.jsp">Admin home</a></div>
	<div align="right"><a href="index.jsp">Logout</a></div>
	
	<hr/>
	<div align=center>
		<h1>Loan List</h1>
	</div>
	<c:forEach items="${listOfLists}" var="loanList" varStatus="count">
		<div align=center>
			<h2><c:out value="${loanList[0].status}"/> Loans</h2>
		<c:set var = "myCount" value = "${loanList[0].status}" />	
		<c:set var = "listOfLists" value = "${listOfLists}" />  	
		<table border="1">
	        <tr>
	            <td>Application#</td>
	            <td>Purpose</td>
	            <td>Amount applied</td>
	            <td>Date of application</td>
	            <td>Business Structure</td>
	            <td>Business Indicator</td>
	            <td>Taxpayer</td>
	            <td>Address</td>
	            <td>Email</td>
	            <td>Mobile</td>
	            <td>Status</td>
	            <% if(pageContext.findAttribute("myCount").equals("Initiated")){
        		%><td><c:out value="Click to Approve/Reject"/></td><%
        		}%>
	        </tr>
	        <c:forEach items="${loanList}" var="loan">
	            <tr> 	 
	            	<td><c:out value="${loan.applno}"/></td>
	            	<td><c:out value="${loan.purpose}"/></td>
		            <td><c:out value="${loan.amtrequest}"/></td>
		            <td><c:out value="${loan.doa}"/></td>
		            <td><c:out value="${loan.bstructure}"/></td>
		            <td><c:out value="${loan.bindicator}"/></td>
		            <td><c:out value="${loan.taxpayer}"/></td>
		            <td><c:out value="${loan.address}"/></td>
		            <td><c:out value="${loan.email}"/></td>
		            <td><c:out value="${loan.mobile}"/></td>
		            <td><c:out value="${loan.status}"/></td>
		            <% if(pageContext.findAttribute("myCount").equals("Initiated")){%>
		            <td>
		            	<form action="admin?action=updatestatus" method="post">
		            	<input type="hidden" id="applno" name="applno" value=<c:out value="${loan.applno}"/>>
		            	<input type="hidden" id="status" name="status" value=<c:out value="${loan.status}"/>>
		            	<input type="submit" name="submit" value="Approve"><input type="submit" name="submit" value="Reject">
		            	</form>
		            </td>
		            	<%} %>
	        	</tr>
			</c:forEach>
	    </table>
	    </div>
    </c:forEach>		
    <jsp:include page="footer.jsp"/>
</body>
</html>