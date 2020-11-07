package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

@RestController
@PreAuthorize("isAuthenticated()")

public class TransfersController {

	private TransfersDAO transferDAO;
	private AccountDAO accountDAO;
	
	public TransfersController(TransfersDAO dao, AccountDAO accountDAO) {
		this.transferDAO = dao;
		this.accountDAO = accountDAO;
	}
	
	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	public List<Transfer> list(Principal principal, @RequestParam(defaultValue = "-1") int id) {
		if(id == -1) {
			int accountId = (accountDAO.getAccountByUsername(principal.getName())).getAccountId();
			return transferDAO.listAllForUser(accountId);
		}else 
			return transferDAO.getTransferByID(id);
	}
	
	@RequestMapping(path = "/transfers/pending", method = RequestMethod.GET)
	public List<Transfer> listPending(Principal principal){
	int accountId = (accountDAO.getAccountByUsername(principal.getName())).getAccountId();
	return transferDAO.listPendingTransfers(accountId);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/transfers", method = RequestMethod.POST)
	public List<Transfer> transfer(@Valid @RequestBody TransferDTO transferDTO, Principal principal) throws Exception{
		//uses the principal to get username, and then uses the username to get the account and then gets the account ID from the username
		int fromAccount = (accountDAO.getAccountByUsername(principal.getName())).getAccountId();
		
		//takes body info from client side and creates transferDTO object
		Transfer transfer = new Transfer();
		transfer.setAmount(transferDTO.getAmount());
		transfer.setAccountTo(transferDTO.getTransferToId());
		transfer.setAccountFrom(fromAccount);
		transfer.setTransferType(transferDTO.getTransferTypeId());
		
		Double fromAccountBalance = accountDAO.getAccountById(fromAccount).getBalance();
		Double newFromAccountBalance = fromAccountBalance - transfer.getAmount();


		if(newFromAccountBalance >= 0) {
			//writes the transfer to the DB
			int id = transferDAO.newTransfer(transfer);
		
			//update balance in fromaccount to subtract amount
			accountDAO.updateBalance(newFromAccountBalance, transfer.getAccountFrom());

			//add amount to toaccount
			Double newToAccountBalance = accountDAO.getAccountById(transfer.getAccountTo()).getBalance() + transfer.getAmount();
			//update balance in toaccount add amount
			accountDAO.updateBalance(newToAccountBalance, transfer.getAccountTo());
			
			//returns transfer ID for confirmation
			return transferDAO.getTransferByID(id);
		}
		else {
			throw new Exception();
		}
	
	}
}
