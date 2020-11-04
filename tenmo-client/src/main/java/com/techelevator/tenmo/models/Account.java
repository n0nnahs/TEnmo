package com.techelevator.tenmo.models;

public class Account {
	private int accountId;
	private int userId;
	private Double balance;
	
	public Account() {
		
	}
	public Account(int accountId, int userId, Double balance) {
		this.accountId = accountId;
		this.userId = userId;
		this.balance = balance;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Account |accountId=" + accountId + ", userId=" + userId + ", balance=" + balance + "|";
	}
}
