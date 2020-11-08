package com.techelevator.tenmo.models;

public class TransferDTO {
	
	//int transferId;
	//I was getting an error because it was defaulting this to 0 when it was sending to the server side since it doesn't get set anywhere 
	//before being sent to server. 
	
	int transferStatusId;
	int transferToId;
	int transferTypeId;
	Double amount;
	
	public int getTransferStatusId() {
		return transferStatusId;
	}
	
	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	
	public int getTransferToId() {
		return transferToId;
	}
	
//	public int getTransferId() {
//		return transferId;
//	}
//
//	public void setTransferId(int transferId) {
//		this.transferId = transferId;
//	}

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
