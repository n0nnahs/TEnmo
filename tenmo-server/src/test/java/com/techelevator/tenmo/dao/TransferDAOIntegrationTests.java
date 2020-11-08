package com.techelevator.tenmo.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransferDAOIntegrationTests {
	
	private static SingleConnectionDataSource dataSource;
	private TransfersSqlDAO dao;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
		
	}
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}
	@Before
	public void setup() {
		dao = new TransfersSqlDAO(dataSource);
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		String deleteTables = "TRUNCATE TABLE transfers, accounts, users";
		jdbc.update(deleteTables);
		
		String insertTestUser = "INSERT INTO users (user_id, username, password_hash) VALUES (?, ?, ?)";
		jdbc.update(insertTestUser, 1, "user1", "password");
		jdbc.update(insertTestUser, 2, "user2", "password");
		
		String insertTestAccount = "INSERT INTO accounts (account_id, user_id, balance) VALUES (?, ?, ?)";
		jdbc.update(insertTestAccount, 1, 1, 1000.00);
		jdbc.update(insertTestAccount, 2, 2, 1000.00);
		
		String insertTestTransfer = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
		"VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		jdbc.update(insertTestTransfer, 2, 2, 1, 2, 100.00);
		jdbc.update(insertTestTransfer, 2, 2, 2, 1, 200.00);
		jdbc.update(insertTestTransfer, 2, 1, 1, 2, 100.00);
		
	}
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	@Test
	public void listAllForUser_returns_all_transfers_for_user() {
		List<Transfer> theTransfers = dao.listAllForUser(1);
		Assert.assertEquals(3, theTransfers.size());
	}
	@Test
	public void listPendingTransfers_lists_all_pending_transfers() {
		List<Transfer> pendingTransfers = dao.listPendingTransfers(1);
		Assert.assertEquals(1, pendingTransfers.size());
		
	}
//	private Transfer getTransfer(int transferId, int transferType, int statusId, int accountFrom, int accountTo, double amount) {
//		Transfer theTransfer = new Transfer();
//		theTransfer.setTransferId(transferId);
//		theTransfer.setTransferType(transferType);
//		theTransfer.setStatusId(statusId);
//		theTransfer.setAccountFrom(accountFrom);
//		theTransfer.setAccountTo(accountTo);
//		theTransfer.setAmount(amount);
//		return theTransfer;
//		 
//	}

}
