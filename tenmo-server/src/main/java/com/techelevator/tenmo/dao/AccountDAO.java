package com.techelevator.tenmo.dao;

import java.util.List;

public interface AccountDAO {
	List<Transaction> listAllTransfers();
	
	int getBalance(int Id);
	
	int decreaseBalance(int amount);
	
	int increaseBalance(int amount);
	
	Transaction getDetails(int transactionId);
	
}
