package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;

@Component
public class TransfersSqlDAO implements TransfersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public TransfersSqlDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Transfer> listAllForUser(int accountId) {
		List<Transfer> transfers = new ArrayList<>();
		String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, username "
				   + "FROM transfers "
				   + "JOIN accounts ON account_from = account_id "
				   + "JOIN users USING(user_id) "
				   + "WHERE account_from = ? OR account_to = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			transfers.add(transferResult);
		}
		return transfers;
	}

	@Override
	public int newTransfer(Transfer transfer) {
		String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
				   + "VALUES	  			(?, 			   2, 				   ?, 			 ?, 		 ?)"
				   + "RETURNING transfer_id";
	
		 Long transferIdLong = jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferType(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		 int transferId = transferIdLong.intValue();
		 return transferId;
	}
	@Override
	public List<Transfer> listPendingTransfers(int accountId){
		List<Transfer> pending = new ArrayList<>();
		String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, username "
				   + "FROM transfers "
				   + "JOIN accounts ON account_from = account_id "
				   + "JOIN users USING(user_id) "
				   + "WHERE (account_from = ? OR account_to = ?) AND transfer_status_id = 1";	
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			pending.add(transferResult);
		} 
		return pending;
	} 
	
	@Override
	public List<Transfer> getTransferByID(int transferId) {
		List<Transfer> transfer = new ArrayList<>();
		String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount, username "
				+ "FROM transfers "
				+ "JOIN accounts ON account_from = account_id "
				+ "JOIN users USING(user_id) "
				+ "WHERE transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
		
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			transfer.add(transferResult);
		}
		return transfer;
		
	}
	
	private Transfer mapRowToTransfer(SqlRowSet results) {
		Transfer transfer = new Transfer();
		
		transfer.setTransferId(results.getInt("transfer_id"));
		transfer.setTransferType(results.getInt("transfer_type_id"));
		transfer.setStatusId(results.getInt("transfer_status_id"));
		transfer.setAccountFrom(results.getInt("account_from"));
		transfer.setAccountTo(results.getInt("account_to"));
		transfer.setAmount(results.getDouble("amount"));
		
		return transfer;
	}

	
}
