package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.model.Transfer;

public class TransfersSqlDAO implements TransfersDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Transfer> listAllForUser(String username) {
		List<Transfer> transfers = new ArrayList<>();
		String sql = "SELECT transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfers JOIN users ON account_from = user_id WHERE user_id = ? ";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			Transfer transferResult = mapRowToTransfer(results);
			transfers.add(transferResult);
		}
		return transfers;
	}

	@Override
	public int newTransfer(int transferType, int statusId, int accountFrom, int accountTo, Double balance) {
		String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
				   + "VALUES	  			(?, 			   ?, 				   ?, 			 ?, 		 ?)"
				   + "RETURNING transfer_id";
		SqlRowSet returningId = jdbcTemplate.queryForRowSet(sql, transferType, statusId, accountFrom, accountTo, balance);
		returningId.next();
		int transferId = returningId.getInt("transfer_id");
		return transferId;
	}

	private Transfer mapRowToTransfer(SqlRowSet results) {
		Transfer transfer = new Transfer();
		
		transfer.setTransferType(results.getInt("transfer_type_id"));
		transfer.setStatusId(results.getInt("transfer_status_id"));
		transfer.setAccountFrom(results.getInt("account_from"));
		transfer.setAccountTo(results.getInt("account_to"));
		transfer.setAmount(results.getDouble("amount"));
		
		return transfer;
	}
	
}
