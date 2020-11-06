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
		String sqlTestUser = "INSERT INTO users (user_id, username, password_hash) values(?, 'test','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC')";
		//String sqlInsertAccount = "INSERT INTO accounts (account_id, user_id, balance) VALUES (999, 999, 1000)";
		dao = new AccountSqlDAO(dataSource);
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.update(sqlTestUser, 999);
		userDAO = new UserSqlDAO(jdbc);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	@Test
	public void updateBalance_updates_account_balance() {
		 
		userDAO.create("test", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC");
		Account updateBalance = new Account();
		 updateBalance.setBalance(100.00);
		 
		 assertEquals(updateBalance.getBalance()+100, updateBalance.getBalance());
	
	}
	@Test
	public void findAll_returns_all_users() {
	
		List<User> allUsers = userDAO.findAll();
		
		userDAO.create("test", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC");
		List<User> allUsersPlusTest = userDAO.findAll();
	
		Assert.assertEquals(allUsers.size() + 1, allUsersPlusTest.size());
		
	}
	
	//not working yet
	@Test	
	public void returns_User_From_Username_Search() {
		boolean testUser = userDAO.create("test", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC");
		User results = userDAO.findByUsername(testUser.getUsername());
		
		assertEquals(testUser, results);
		
		
		
	}
	@Test
	public void list_returns_list_of_accounts() {
		List<Account> listAccounts = dao.list();
		
		Account theAccount = getAccount(1000.00, 999);
		dao.save(theAccount);
		List<Account> listAccountsPlusTest = dao.list();
		
		assertEquals(listAccounts.size() + 1, listAccountsPlusTest.size());
		}
	
	@Test
	public void returns_Account_From_Username_Search() {
		
		Account theAccount = getAccount(1000.00, 999);
		dao.save(theAccount);
		Account results = dao.getAccountByUsername("test");
		assertAccountsAreEqual(theAccount, results);
		
	}
	private User getUser(String username) {
		User theUser = new User();
		theUser.setUsername(username);
		return theUser;
	}
	private Account getAccount(Double balance, int userId) {
		Account theAccount = new Account();
		theAccount.setUserId(userId);
		theAccount.setBalance(balance);
		return theAccount;
	}
	private void assertAccountsAreEqual(Account expected, Account actual) {
		assertEquals(expected.getAccountId(), actual.getAccountId());
		assertEquals(expected.getUserId(), actual.getUserId());
		assertEquals(expected.getBalance(), actual.getBalance());
	}

}
