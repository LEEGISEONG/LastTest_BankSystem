package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;

public class BankTransferService implements BankService {

	@Override
	public void execute(Model model) {
		HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
		String sendMemberId = request.getParameter("sendMemberId");
		String receiveMemberId = request.getParameter("receiveMemberId");
		String transferBalance = request.getParameter("transferBalance");
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		Connection con = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);

			BankDAO dao = new BankDAO(con);
			// ���
			boolean withdrawResult = dao.updateWithdraw(sendMemberId, transferBalance);

			// �Ա� 
			boolean depositResult = dao.updateDeposit(receiveMemberId, transferBalance);
			
			dao.transforHistory(sendMemberId, receiveMemberId, transferBalance);

			if (withdrawResult && depositResult) {
				con.commit();

			} else {
				con.rollback();
			}
			
			
			model.addAttribute("RESULT", withdrawResult && depositResult);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {

			}
		}
	}

}
