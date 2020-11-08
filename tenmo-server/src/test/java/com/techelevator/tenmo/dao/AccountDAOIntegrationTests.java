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
import com.techelevator.tenmo.model.User;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountDAOIntegrationTests {
	
	private static SingleConnectionDataSource dataSource;
	private AccountSqlDAO dao;
	private UserSqlDAO userDAO;
	private JdbcTemplate jdbc;

	
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
		dao = new AccountSqlDAO(dataSource);
		jdbc = new JdbcTemplate(dataSource);
		userDAO = new UserSqlDAO(jdbc);
		
		String deleteTables = "TRUNCATE TABLE accounts, transfers, users";
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
	public void findAll_returns_all_users() {
	
		List<User> allUsers = userDAO.findAll();
		
		userDAO.create("test", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC");
		List<User> allUsersPlusTest = userDAO.findAll();
	
		Assert.assertEquals(allUsers.size() + 1, allUsersPlusTest.size());
		
	}
		

	@Test
	public void list_returns_list_of_accounts() {
		List<Account> listAccounts = dao.list(1);
		String extraTestUserSql = "INSERT INTO users (user_id, username, password_hash) VALUES (?, ?, ?)";
		jdbc.update(extraTestUserSql, 3, "user3", "password");
		String extraTestAccountSql = "INSERT INTO accounts (account_id, user_id, balance) VALUES (?, ?, ?)";
		jdbc.update(extraTestAccountSql, 3, 3, 1000.00);
		List<Account> listAccountsPlusTest = dao.list(1);
		
		assertEquals(listAccounts.size() + 1, listAccountsPlusTest.size());
		}
	
	@Test
	public void returns_Account_From_Username() {
		String extraTestUserSql = "INSERT INTO users (user_id, username, password_hash) VALUES (?, ?, ?)";
		jdbc.update(extraTestUserSql, 3, "user3", "password");
		String extraTestAccountSql = "INSERT INTO accounts (account_id, user_id, balance) VALUES (?, ?, ?)";
		jdbc.update(extraTestAccountSql, 3, 3, 1000.00);
		Account results = dao.getAccountByUsername("user3");
		assertEquals(3, results.getAccountId());
		
	}
	@Test
 	public void updateBalance_updates_account_balance() {

		Account account = dao.getAccountById(userDAO.findIdByUsername("user1"));
		
		Double before = account.getBalance();
		
		Double after = dao.updateBalance(account.getBalance()+100.00, account.getAccountId());
		
		assertEquals(before+100.00, after);
 	}
	

}
