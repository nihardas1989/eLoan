package com.iiht.evaluation.eloan.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iiht.evaluation.eloan.dao.ConnectionDao;
import com.iiht.evaluation.eloan.dto.LoanDto;
import com.iiht.evaluation.eloan.model.ApprovedLoan;
import com.iiht.evaluation.eloan.model.LoanInfo;


@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConnectionDao connDao;
	
	public void setConnDao(ConnectionDao connDao) {
		this.connDao = connDao;
	}
	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");
		System.out.println(jdbcURL + jdbcUsername + jdbcPassword);
		this.connDao = new ConnectionDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action =  request.getParameter("action");
		System.out.println(action);
		String viewName = "";
		try {
			switch (action) {
			case "listall" : 
				viewName = listall(request, response);
				break;
			case "process":
				viewName=process(request,response);
				break;
			case "callemi":
				viewName=calemi(request,response);
				break;
			case "updatestatus":
				viewName=updatestatus(request,response);
				break;
			case "logout":
				viewName = adminLogout(request, response);
				break;	
			default : viewName = "notfound.jsp"; break;		
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);
		
		
	}

	private String updatestatus(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		/* write the code for updatestatus of loan and return to admin home page */
		String applno = request.getParameter("applno");
		String status = request.getParameter("status");
		LocalDate today = LocalDate.now();
		
		if(status.equals("Initiated")) {
			String submit = request.getParameter("submit");
			if(submit.equals("Approve")) {
				this.connDao.updateLoanstatus(applno,"Approved");
				ApprovedLoan loan = new ApprovedLoan(applno, 0, 0,Date.valueOf(today),Date.valueOf(today) , 0);
				this.connDao.addNewApprovedLoan(loan);
			}
			else {
				this.connDao.updateLoanstatus(applno,"Rejected");
			}
		}
		else {			
			this.connDao.updateLoanstatus(applno,"Processed");
		}
		return "adminhome1.jsp";
	}
	@SuppressWarnings("deprecation")
	private String calemi(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
	/* write the code to calculate emi for given applno and display the details */
		String applno = request.getParameter("applno");
		this.connDao.checkLoan(applno);
		
		String loanStatus = this.connDao.getLoanStatus(applno);
		if(loanStatus.equals("Approved")) {
			this.connDao.updateLoanstatus(applno, "Processed");
			request.setAttribute("applno", applno);
			return "calemi.jsp";
		}
		else if(loanStatus.equals("Processed")) {
			Date lcd = Date.valueOf(request.getParameter("psd"));
			lcd.setMonth(lcd.getMonth()+Integer.valueOf(request.getParameter("loanterm")));
			ApprovedLoan loan = new ApprovedLoan(request.getParameter("applno"), Integer.valueOf(request.getParameter("amotsanctioned")), Integer.valueOf(request.getParameter("loanterm")),Date.valueOf(request.getParameter("psd")),lcd , 0);
			int interest = Integer.valueOf(request.getParameter("interest"));
			int termPaymentAmount = 0;
			termPaymentAmount = (int) (loan.getAmotsanctioned() * Math.pow((1 + interest/100),loan.getLoanterm()));
			loan.setEmi((int) ((termPaymentAmount ) / (loan.getLoanterm())));
			
			this.connDao.updateApprovedLoan(loan);
			return "adminhome1.jsp";
		}
		throw new SQLException("This loan is not 'Approved'.");
	}
	private String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
	/* return to process page */
		return  "process.jsp";
	}
	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	/* write code to return index page */
		return "index.jsp";
	}

	private String listall(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
	/* write the code to display all the loans */
		List<List<LoanInfo>> listOfLists = this.connDao.loanList();
		if(listOfLists.size()==0) {
			throw new SQLException("No loans in database.");			
		}
		request.setAttribute("listOfLists", listOfLists);
		return "listall.jsp";
	}

	
}