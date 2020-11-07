package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransfersDAO {
	
	List<Transfer> listAllForUser(int accountId);
	
	List<Transfer> listPendingTransfers(int accountId);
	
	void newTransfer(Transfer transfer); 
	
}
