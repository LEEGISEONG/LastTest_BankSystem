package com.cjon.bank.dto;

public class BankInquiryDTO {
	private String sendMemberId;
	private String receiveMemberId;
	private int transferMoney;
	
	
	public String getSendMemberId() {
		return sendMemberId;
	}
	public void setSendMemberId(String sendMemberId) {
		this.sendMemberId = sendMemberId;
	}
	public String getReceiveMemberId() {
		return receiveMemberId;
	}
	public void setReceiveMemberId(String receiveMemberId) {
		this.receiveMemberId = receiveMemberId;
	}
	public int getTransferMoney() {
		return transferMoney;
	}
	public void setTransferMoney(int transferMoney) {
		this.transferMoney = transferMoney;
	}
	
}

