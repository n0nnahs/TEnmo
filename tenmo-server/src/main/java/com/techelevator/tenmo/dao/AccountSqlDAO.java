package com.techelevator.tenmo.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class AccountSqlDAO implements AccountDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public AccountSqlDAO() {
        this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public double getBalance(int id) {
		String sql = "SELECT balace FROM accounts WHERE user_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		
		return results.getDouble(1);
	}

	@Override
	public double updateBalance(int amount, int id) {
		String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
		jdbcTemplate.update(sql, amount, id);
		
		return getBalance(id);
	}



}
