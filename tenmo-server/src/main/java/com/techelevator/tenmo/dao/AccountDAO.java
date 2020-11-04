package com.techelevator.tenmo.dao;

import java.util.List;

public interface AccountDAO {

	double getBalance(int Id);
		
	double updateBalance(int id, int amount);
	
}
