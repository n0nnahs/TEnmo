package com.techelevator.tenmo.services;
import java.util.List;

import com.techelevator.tenmo.models.User;
public interface UserService {
	
	User getAccountBalance(int id);
	
	User sendBucks();
	
	User requestBucks();
	
	User viewTransferHistory();
	
	User viewPendingTransfers();
	
	User listTransactionsForAccount();
	
	User getTransactionsDetails();
}
