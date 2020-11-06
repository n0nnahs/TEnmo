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
				   + "VALUES	  			(?, 			   ?, 				   ?, 			 ?, 		 ?)"
				   + "RETURNING transfer_id";
		SqlRowSet returningId = jdbcTemplate.queryForRowSet(sql, transfer.getTransferType(), transfer.getStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		returningId.next();
		int transferId = returningId.getInt("transfer_id");
		return transferId;
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
