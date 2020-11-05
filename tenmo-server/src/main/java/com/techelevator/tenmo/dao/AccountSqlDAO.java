package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;


@Component
public class AccountSqlDAO implements AccountDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public AccountSqlDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public double getBalance(int id) {
		String sql = "SELECT balance FROM accounts WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		return results.getDouble(1);
	}

	@Override
	public double updateBalance(int amount, int id) {
		String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
		jdbcTemplate.update(sql, amount, id);
		
		return getBalance(id);
	}

	@Override
	public List<Account> list() {
		List<Account> accounts = new ArrayList<>();
		String sql = "SELECT user_id, account_id, username, balance FROM accounts JOIN users USING(user_id)";
				
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			Account accountResult = mapRowToAccount(results);
			accounts.add(accountResult);
		}
		
		return accounts;
	} 

	@Override
	public Account getAccount(int id) {
		String sql = "SELECT user_id, balance FROM accounts WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		if(results.next()) {
			return mapRowToAccount(results);
		}
		else {
			return null;
		}
		
	}

	private Account mapRowToAccount(SqlRowSet results) {
		Account account = new Account();
		account.setAccountId(results.getInt("account_id"));
		account.setUsername(results.getString("username"));
		account.setBalance(results.getDouble("balance"));
		account.setUserId(results.getInt("user_id"));
		
		return account;
	}



}
