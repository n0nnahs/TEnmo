package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransfersDAO {
	
	List<Transfer> listAllForUser(int accountId);
	
	int newTransfer(int transferType, int statusId, int accountFrom, int accountTo, Double balance); 
	
}
