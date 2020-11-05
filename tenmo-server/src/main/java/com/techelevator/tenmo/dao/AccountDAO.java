package com.techelevator.tenmo.dao;

import java.util.List;
import com.techelevator.tenmo.model.Account;

public interface AccountDAO {
	
	List<Account> list();
	
	Account getAccount(int id);
			
	Double updateBalance(int id, int amount);
	
}
