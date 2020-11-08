package com.techelevator.tenmo.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TransferDTO {
	
	@NotNull
	int transferToId;
	
	@Positive(message = "Amount to send cannot be negative amount")
	Double amount;
	
	@NotNull
	int transferTypeId;
	
	@NotNull
	int transferStatusId;
	
	public int getTransferToId() {
		return transferToId;
	}
	
	public void setTransferToId(int transferToId) {
		this.transferToId = transferToId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	public int getTransferStatusId() {
		return transferStatusId;
	}

	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}


}
