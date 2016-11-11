package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.dto.BankInquiryDTO;

public class BankDAO {

	private Connection con;

	public BankDAO(Connection con) {
		this.con = con;
	}

	public ArrayList<BankDTO> selectAll() {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();

		try {

			String sql = "select * from bank_member_tb";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);

			}

		} catch (Exception e) {

		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}
		return list;
	}

	public ArrayList<BankDTO> search(String memberID) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();
		try {
			String sql = "select * from bank_member_tb where member_id like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + memberID + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();
				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	public boolean updateDeposit(String memberID, String memberBalance) {

		PreparedStatement pstmt = null;
		boolean result = false;

		String sql = "update bank_member_tb set member_balance=member_balance+? where member_id=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(memberBalance));
			pstmt.setString(2, memberID);

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {

			}
		}

		return result;
	}

	public boolean updateWithdraw(String memberID, String memberBalance) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		int balance = Integer.parseInt(memberBalance);
		String sql = "update bank_member_tb set member_balance=member_balance-? where member_id=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, balance);
			pstmt.setString(2, memberID);

			int count = pstmt.executeUpdate();

			if (count == 1) {
				String sql1 = "select member_balance from bank_member_tb where member_id=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1, memberID);
				rs = pstmt1.executeQuery();
				if (rs.next()) {
					balance = rs.getInt("member_balance");
				}
				if (balance < 0) {
					result = false;
				} else {

					result = true;
				}

			} else {
				result = false;
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {

			}
		}

		return result;
	}

	public boolean transforHistory(String sendMemberId, String receiveMemberId, String balance) {
		PreparedStatement pstmt = null;
		String sql = "insert into bank_transfer_history_tb values(HISTORY_SQ, ?, ?, ?)";
		boolean result = false;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sendMemberId);
			pstmt.setString(2, receiveMemberId);
			pstmt.setInt(3, -(Integer.parseInt(balance)));

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
			}

			pstmt.setString(1, receiveMemberId);
			pstmt.setString(2, sendMemberId);
			pstmt.setInt(3, Integer.parseInt(balance));
			int count1 = pstmt.executeUpdate();

			if (count1 == 1) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean depositHistory(String memberId, String balance) {
		PreparedStatement pstmt = null;
		String sql = "insert into bank_transfer_history_tb values(HISTORY_SQ, ?, ?, ?)";
		boolean result = false;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberId);
			pstmt.setInt(3, Integer.parseInt(balance));

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean withdrawHistory(String memberId, String balance) {
		PreparedStatement pstmt = null;
		String sql = "insert into bank_transfer_history_tb values(HISTORY_SQ, ?, ?, ?)";
		boolean result = false;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberId);
			pstmt.setInt(3, -(Integer.parseInt(balance)));

			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public ArrayList<BankInquiryDTO> inquiry(String memberID) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankInquiryDTO> list = new ArrayList<BankInquiryDTO>();
		try {
			String sql = "select * from bank_transfer_history_tb where send_member_id like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + memberID + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankInquiryDTO dto = new BankInquiryDTO();
				dto.setSendMemberId(rs.getString("send_member_id"));
				dto.setReceiveMemberId(rs.getString("receive_member_id"));
				dto.setTransferMoney(rs.getInt("transfer_money"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}
}
