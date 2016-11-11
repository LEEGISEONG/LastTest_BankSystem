package com.cjon.bank.service;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankInquiryDTO;

public class BankInquiryService implements BankService {

	@Override
	public void execute(Model model) {
		HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
		String memberID = request.getParameter("memberId");
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		Connection con = null;
		ArrayList<BankInquiryDTO> list = null;

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);

			BankDAO dao = new BankDAO(con);
			list = dao.inquiry(memberID);

			model.addAttribute("RESULT", list);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {		
				e.printStackTrace();
			}
		}

	}

}
