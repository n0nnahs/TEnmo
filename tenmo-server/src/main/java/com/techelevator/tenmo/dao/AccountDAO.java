package com.techelevator.tenmo.dao;

import java.util.List;
import com.techelevator.tenmo.model.Account;

public interface AccountDAO {
	
	List<Account> list();
				
	Double updateBalance(int id, int amount);

	Account getAccountById(int id);
	
	Account getAccountByUsername(String username);
	
}
