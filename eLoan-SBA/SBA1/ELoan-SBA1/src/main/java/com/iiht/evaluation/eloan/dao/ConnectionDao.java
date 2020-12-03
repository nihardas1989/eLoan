package com.iiht.evaluation.eloan.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;

import com.iiht.evaluation.eloan.dto.LoanDto;
import com.iiht.evaluation.eloan.model.ApprovedLoan;
import com.iiht.evaluation.eloan.model.LoanInfo;
import com.iiht.evaluation.eloan.model.User;

public class ConnectionDao {
	private static final long serialVersionUID = 1L;
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ConnectionDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	public  Connection connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
		return jdbcConnection;
	}

	public void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	// put the relevant DAO methods here..
	public List<User> getUserList() throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM `user`";
		this.connect();
		
		Statement stmt = this.jdbcConnection.createStatement();
		ResultSet rs =  stmt.executeQuery(sql);
		
		List<User> users = new ArrayList<User>();
		
		while(rs.next()) {
			User user = new User(rs.getString(1),rs.getString(2));
			users.add(user);
		}
		
		rs.close();
		stmt.close();
		this.disconnect();
		return users;
	}

	public boolean addNewUser (User user) throws ClassNotFoundException, SQLException {
		//Used for new user registration
		String sql = "INSERT INTO `user` (`username`, `password`) VALUES(?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		
		if(this.checkUser(user)) {
			return false;
		}
		pstmt.executeUpdate();
		pstmt.close();
		
		this.disconnect();
		return true;
	}
	
	public boolean validate (User user) throws ClassNotFoundException, SQLException {
		//Used to validate login
		String sql = "SELECT * FROM `user` WHERE `username` = ? AND `password` = ?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		
		ResultSet rs = pstmt.executeQuery();
				
		if(rs.next())
			return true;
		return false;
	}
	
	public boolean checkUser (User user) throws ClassNotFoundException, SQLException {
		//Used to check if user exists
		String sql = "SELECT * FROM `user` WHERE `username` = ?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		
		ResultSet rs = pstmt.executeQuery();
				
		if(rs.next())
			return true;
		return false;
	}
	
	public boolean checkLoan (String applno) throws ClassNotFoundException, SQLException {
		//Used to check if loan exists with given application no
		String sql = "SELECT * FROM `loan_info` WHERE `applno` = ?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, applno);
		
		ResultSet rs = pstmt.executeQuery();
				
		if(rs.next())
			return true;
		throw new SQLException("Invalid application number.");
	}

	public boolean addNewLoan (LoanInfo loan) throws ClassNotFoundException, SQLException {
		//Used to create new loan entry in DB
		String sql = "INSERT INTO `loan_info` (`purpose`, `amtrequest`, `doa`, `bstructure`, `bindicator`, `address`, `email`, `mobile`, `status`, `taxpayer`) VALUES (?,?,?,?,?,?,?,?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		
		pstmt.setString(1, loan.getPurpose());
		pstmt.setLong(2, loan.getAmtrequest());
		pstmt.setDate(3, loan.getDoa());
		pstmt.setString(4, loan.getBstructure());
		pstmt.setString(5, loan.getBindicator());
		pstmt.setString(6, loan.getAddress());
		pstmt.setString(7, loan.getEmail());
		pstmt.setString(8, loan.getMobile());
		pstmt.setString(9, loan.getStatus());
		pstmt.setString(10, loan.getTaxpayer());
		
		int n = pstmt.executeUpdate();
		
		pstmt.close();
		this.disconnect();
		
		if(n>0)
			return true;
		return false;
	}
	
	public int countLoan() throws SQLException {
		//Used to count number of loans created
		String sql = "SELECT COUNT(applno) FROM `loan_info`";
		int n=0;
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		n = rs.getInt(1);
		
		pstmt.close();
		this.disconnect();
		
		return n;
	}
	
	public LoanInfo getLoan (String applno) throws ClassNotFoundException, SQLException {
		//Fetch loan info based on application number
		String sql = "SELECT * FROM `loan_info` WHERE `applno` = ?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, applno);
		
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		
		LoanInfo loan = new LoanInfo(rs.getString(1), rs.getString(2), rs.getInt(3), Date.valueOf(rs.getString(4)), rs.getString(5),rs.getString(6),
				rs.getString(11),rs.getString(7),rs.getString(8), rs.getString(9),rs.getString(10));
		pstmt.close();
		this.disconnect();
		
		return loan;
	}
	
	public String getLoanStatus(String applno) throws SQLException {
		//Fetch loan status based on application number
		String sql = "SELECT `status` FROM `loan_info` WHERE `applno` = (?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, applno);
		
		ResultSet rs = pstmt.executeQuery();
				
		rs.next();
		return rs.getString(1);
	}
	
	public boolean editLoan(LoanInfo loan) throws SQLException {
		//Edit loan based on application number
		String sql = "UPDATE `loan_info` SET `purpose`=?,`amtrequest`=?,`bstructure`=?,`bindicator`=?,`address`=?,`email`=?,`mobile`=?,`taxpayer`=? WHERE `applno` = ?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, loan.getPurpose());
		pstmt.setLong(2, loan.getAmtrequest());
		pstmt.setString(3, loan.getBstructure());
		pstmt.setString(4, loan.getBindicator());
		pstmt.setString(5, loan.getAddress());
		pstmt.setString(6, loan.getEmail());
		pstmt.setString(7, loan.getMobile());
		pstmt.setString(8, loan.getTaxpayer());
		pstmt.setString(9, loan.getApplno());
		
		int n = pstmt.executeUpdate();
		pstmt.close();
		this.disconnect();
		
		if(n>0)
			return true;
		return false;
	}
	
	public List<List<LoanInfo>> loanList() throws SQLException{
		//Generate loan list based on loan status
		String sql = "SELECT * FROM `loan_info` WHERE `status`=?";
		List<String> status = new ArrayList<String>();
		status.add("Initiated");
		status.add("Processed");
		status.add("Rejected");
		int count=0;
		
		List<List<LoanInfo>> listOfLists = new ArrayList<List<LoanInfo>>();
		if((this.approvedLoanList()).size()>0) {
			listOfLists.add(this.approvedLoanList());
		}
		this.connect();
		
		while(count<status.size()) {
			PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
			pstmt.setString(1, status.get(count));
			ResultSet rs = pstmt.executeQuery();
			List<LoanInfo> loan = new ArrayList<LoanInfo>();
			while(rs.next()) {
				loan.add(new LoanInfo(rs.getString(1), rs.getString(2), rs.getInt(3), Date.valueOf(rs.getString(4)), rs.getString(5),rs.getString(6),
						rs.getString(11),rs.getString(7),rs.getString(8), rs.getString(9),rs.getString(10)));
			}
			if(loan.size()>0) {
				listOfLists.add(loan);
			}
			pstmt.close();
			rs.close();
			count++;
		}
		this.disconnect();
		return listOfLists;
	}
	
	public List<LoanInfo> approvedLoanList() throws SQLException{
		//Generate approved loan list
		String sql = "SELECT * FROM `approved_loan`";
		String appsql = "SELECT * FROM `loan_info` WHERE `status`= \"Approved\" AND `applno`=?";
		List<ApprovedLoan> loan = new ArrayList<ApprovedLoan>();
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			loan.add(new ApprovedLoan(rs.getString(1), rs.getInt(2), rs.getInt(3), Date.valueOf(rs.getString(4)), Date.valueOf(rs.getString(5)), rs.getInt(6)));
		}
		
		List<LoanInfo> loanList = new ArrayList<LoanInfo>();

		for(int i=0;i<loan.size();i++) {
			PreparedStatement stmt = this.jdbcConnection.prepareStatement(appsql);
			stmt.setString(1, loan.get(i).getApplno());
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				loanList.add(new LoanInfo(result.getString(1), result.getString(2), result.getInt(3), Date.valueOf(result.getString(4)), result.getString(5),result.getString(6),
					result.getString(11),result.getString(7),result.getString(8), result.getString(9),result.getString(10)));
			}		
		}
		return loanList;
	}
	
	public boolean addNewApprovedLoan (ApprovedLoan loan) throws ClassNotFoundException, SQLException {
		//Add new approved loan
		String sql = "INSERT INTO `approved_loan` (`applno`, `amotsanctioned`, `loanterm`, `psd`, `lcd`, `emi`) VALUES (?,?,?,?,?,?);";
		this.connect();
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		
		pstmt.setString(1, loan.getApplno());
		pstmt.setLong(2, loan.getAmotsanctioned());
		pstmt.setLong(3, loan.getLoanterm());
		pstmt.setDate(4, loan.getPsd());
		pstmt.setDate(5, loan.getLcd());
		pstmt.setLong(6, loan.getEmi());
		
		int n = pstmt.executeUpdate();
				
		pstmt.close();
		this.disconnect();
		
		if(n>0)
			return true;
		return false;
	}
	
	public boolean updateApprovedLoan (ApprovedLoan loan) throws ClassNotFoundException, SQLException {
		//Update approved loan based on application number after calculating emi
		String sql = "UPDATE `approved_loan` SET `amotsanctioned`=?,`loanterm`=?,`psd`=?,`lcd`=?,`emi`=? WHERE `applno` = ?";
		this.connect();
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		
		pstmt.setLong(1, loan.getAmotsanctioned());
		pstmt.setLong(2, loan.getLoanterm());
		pstmt.setDate(3, loan.getPsd());
		pstmt.setDate(4, loan.getLcd());
		pstmt.setLong(5, loan.getEmi());
		pstmt.setString(6, loan.getApplno());
		
		int n = pstmt.executeUpdate();
				
		pstmt.close();
		this.disconnect();
		
		if(n>0)
			return true;
		return false;
	}
	
	public boolean updateLoanstatus (String applno, String status) throws ClassNotFoundException, SQLException {
		//Update loan status based on application number
		String updateStatus = "UPDATE `loan_info` SET `status`=? WHERE `applno` = ?";
		this.connect();
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(updateStatus);
		
		pstmt.setString(1, status);
		pstmt.setString(2, applno);
		
		int n = pstmt.executeUpdate();
				
		pstmt.close();
		this.disconnect();
		
		if(n>0)
			return true;
		return false;
	}
}
