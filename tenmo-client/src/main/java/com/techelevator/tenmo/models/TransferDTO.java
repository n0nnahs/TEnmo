package com.techelevator.tenmo.models;

public class TransferDTO {
	int transferId;
	int transferToId;
	int transferTypeId;
	Double amount;
	
	
	public int getTransferToId() {
		return transferToId;
	}
	
	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
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
}
