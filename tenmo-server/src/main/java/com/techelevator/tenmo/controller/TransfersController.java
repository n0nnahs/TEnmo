package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfer;

@RestController
@PreAuthorize("isAuthenticated()")

public class TransfersController {

	private TransfersDAO transfersDAO;
	private AccountDAO accountDAO;
	
	public TransfersController(TransfersDAO dao, AccountDAO accountDAO) {
		this.transfersDAO = dao;
		this.accountDAO = accountDAO;
	}
	
	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	public List<Transfer> list(Principal principal) {
		int accountId = (accountDAO.getAccountByUsername(principal.getName())).getAccountId();
		return transfersDAO.listAllForUser(accountId);
	}
}